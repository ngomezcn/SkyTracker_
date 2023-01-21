package com.example.newRoutes

import com.example.html.AppLayout
import com.example.html.satellite.satelliteDetail
import com.example.loggedUser
import com.example.orm.tables.SatCommentDAO
import com.example.orm.tables.SatelliteDAO
import com.example.orm.tables.SatellitesTable
import com.example.orm.tables.UserTrackingSatDAO
import com.example.pathAssetsSats
import com.example.repositories.SatCommentRepository
import com.example.repositories.SatellitesRepository
import com.example.templates.content.satellitesList
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import io.ktor.server.resources.post
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import java.io.File
import java.nio.file.Files

@Serializable
@Resource("satellites")
class Satellites(val page : Int? = 1, val itemsPerPage : Int? = 5){
    @Serializable
    @Resource("{noradId}")
    class NoradId(val parent: Satellites = Satellites(), val noradId: String) {
        @Resource("message")
        class Message(val parent: NoradId) {
           /// @Resource("new")
           // class New(val parent: Message = Message())
        }
    }
}

fun Route.satelliteRoutes() {

    val satellitesRepository = SatellitesRepository()
    val satCommentRepository = SatCommentRepository()

    get<Satellites> {
        var page : Int? = call.request.queryParameters["page"]?.toInt()
        var itemsPerPage : Int? = call.request.queryParameters["itemsPerPage"]?.toInt()

        if(page == null)
            page = 1
        if(itemsPerPage == null)
            itemsPerPage = 500

        var sats = satellitesRepository.getAllInOrbit()
        var toSlice : IntRange = ((page * itemsPerPage).toInt()..(page * itemsPerPage+itemsPerPage).toInt())
        if (toSlice.last > sats.size)
        {
            toSlice = (toSlice.first until sats.size)
        }
        sats = sats.slice(toSlice)

        call.respondHtmlTemplate(AppLayout(application)) {

            content{
                transaction {
                    satellitesList(application, sats)
                }
            }
        }
    }

    get<Satellites.NoradId> {

        val id : String = if (call.parameters["noradId"].isNullOrBlank()) "" else call.parameters["noradId"]!!
        val sat = satellitesRepository.find(id)


        var b = UserTrackingSatDAO.isSatTrackedByUser(satId = sat.id, userId = loggedUser!!.id)


        if(sat == null) {
            call.respondRedirect(application.href(Satellites()))
        } else
        {
            val comments = satCommentRepository.getAllBySatellite(sat)

            call.respondHtmlTemplate(AppLayout(application)) {
                content{
                    transaction {
                        satelliteDetail(sat, comments, ,application)
                    }
                }
            }
        }
    }

    post<Satellites.NoradId> {
        if(loggedUser == null)
            call.respondRedirect(application.href(Account.SignIn()))

        val data = call.receiveMultipart()
        var _idSatellite = ""
        var _comment = ""
        var _imageName = ""

        data.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name){
                        "idSatellite" -> _idSatellite = part.value
                        "message" -> _comment = part.value
                    }
                }
                is PartData.FileItem -> {
                    var fileBytes = part.streamProvider().readBytes()

                    if(fileBytes.isNotEmpty())
                    {
                        val uuid: UUID = UUID.randomUUID()
                        _imageName =  loggedUser!!.email.take(4)+"-"+uuid.toString()+"."+part.originalFileName!!.substringAfterLast('.', "")
                        Files.createDirectories(pathAssetsSats.toPath());
                        File("$pathAssetsSats/$_imageName").writeBytes(fileBytes)
                    }
                }
                else -> {
                }
            }
        }

        if(_comment.isEmpty()) {
            call.respondText("Comment is empty", status = HttpStatusCode.NotFound)
        }

        var satToComment: SatelliteDAO? = null
        transaction {
            val query = SatellitesTable.select {
                SatellitesTable.id eq _idSatellite.toInt()
            }

            if (SatelliteDAO.wrapRows(query).count().toInt() > 0) {
                satToComment = SatelliteDAO.wrapRows(query).toList().first()
            }
        }

        if(satToComment == null) {
            call.respondText("Satellite does not exist", status = HttpStatusCode.NotFound)
        }

        var commentDAO : SatCommentDAO? = null
        transaction {
            commentDAO = SatCommentDAO.new {
                idSatellite = satToComment!!
                idUser = loggedUser!!
                comment = _comment
                imageName = _imageName
                upVotes = 0
            }
        }

        call.respondRedirect(application.href(Satellites.NoradId(noradId = satToComment!!.noradCatId)))
    }
}
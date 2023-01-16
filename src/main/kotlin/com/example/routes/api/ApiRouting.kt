package com.example.routes.api

import com.example.loggedUser
import com.example.orm.models.*
import com.example.orm.modelsoSatellite.UserDAO
import com.example.orm.modelsoSatellite.UsersTable
import com.example.pathAssetsSats
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

fun Route.apiRouting() {
    route("api") {
        post("create_account") {

            val data = call.receiveMultipart()

            var _username = ""
            var _firstName = ""
            var _surname = ""
            var _email = ""
            var _password = ""

            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        transaction {
                            when (part.name){
                                "username" -> _username = part.value
                                "firstName" -> _firstName = part.value
                                "surname" -> _surname = part.value
                                "email" -> _email = part.value
                                "password" -> _password = part.value
                            }
                        }
                    }
                    is PartData.FileItem -> {

                    }
                    else -> {
                    }
                }}

            var user : UserDAO? = null
            try {
                transaction {
                    user = UserDAO.new {
                        email = _email
                        username = _username
                        firstName = _firstName
                        surname = _surname
                        password = _password
                        sessionCookie = ""
                    }
                }
            } catch (e : Exception) {
                call.respondRedirect("/create_account?errorMsg=\"Error: \" ${e.message}")
                throw e
            }

            loggedUser = user
            call.respondRedirect("/satellites")
        }

        post("sign_in") {

            val data = call.receiveMultipart()

            var email = ""
            var password = ""

            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        transaction {
                            when (part.name){
                                "email" -> email = part.value
                                "password" -> password = part.value
                            }
                        }
                    }
                    is PartData.FileItem -> {                    }
                    else -> {
                    }
                }
            }

            transaction {
                addLogger(StdOutSqlLogger)

                val query = UsersTable.select {
                    (UsersTable.email eq email) and (UsersTable.password eq password)
                }

                if(UserDAO.wrapRows(query).count().toInt() > 0) {
                    loggedUser =  UserDAO.wrapRows(query).toList().first()
                }
            }

            if(loggedUser == null) {
                call.respondRedirect("/sign_in?errorMsg=\"Datos incorrectos\"")
            } else
            {
                call.respondRedirect("/satellites")
            }

        }

        post("post_message") {
            if(loggedUser == null)
                call.respondRedirect("/sign_in")

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

            call.respondRedirect("/view_satellite?id="+satToComment!!.noradCatId)
        }

        post("track_satellite") {
            if(loggedUser == null)
                call.respondRedirect("/sign_in")

            val data = call.receiveMultipart()
            var _idSatellite = 0

            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        when (part.name){
                            "idSatellite" -> _idSatellite = part.value.toInt()
                        }
                    }
                    is PartData.FileItem -> {

                    }
                    else -> {
                    }
                }
            }

            var oSatellite: SatelliteDAO? = SatelliteDAO.getSatellite(_idSatellite)

            if(oSatellite == null)
            {
                call.respond("Not found")
            }

            transaction {
                UserTrackingSatDAO.new {
                    idSatellite = oSatellite!!.id
                    idUser = loggedUser!!.id
                }
            }

            call.respondRedirect("/view_satellite?id="+oSatellite!!.noradCatId!!)
        }
    }
}
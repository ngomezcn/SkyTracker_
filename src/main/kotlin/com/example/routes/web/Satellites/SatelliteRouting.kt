package com.example.routes.web.Satellites

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.SatellitesTable
import com.example.routes.web.WebRoutesEnum
import com.example.routes.web.account.accountWebRouting
import com.example.templates.LayoutTemplate
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.server.resources.*
import kotlinx.serialization.encodeToString

@Serializable
@Resource("satellites")
class Satellites(){

    @Serializable
    @Resource("id/{id?}")
    data class id(val parent: Satellites = Satellites()) {
        override fun toString(): String {
            return
        }
    }
}

fun Route.SatelliteRouting() {

    route("/") {

        get<Satellites> {
            var requestedPage : Int? = null

            if(!call.request.queryParameters["page"].isNullOrBlank()) {
                requestedPage = call.request.queryParameters["page"]!!.toInt()
                if(requestedPage.toInt() > 50)
                    call.respondRedirect("/satellites/id/50")
                if(requestedPage.toInt() < 1)
                    call.respondRedirect("/satellites")
            }

            call.respondHtmlTemplate(LayoutTemplate()) {
                route = WebRoutesEnum.satellites.route

                header {
                    +"List"
                }

                this.satelliteListContent {
                    page = requestedPage
                }
            }

            val url :String = application.href(Satellites.id())
            println(url)
            println(Satellites.id())

            //call.respondRedirect("/satellites/id/50")
            //call.respondRedirect(Satellites.id().toString(5))

        }

        get<Satellites.id> {
            var selectedSat : SatelliteDAO? = null

            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest)

            transaction {

                val temp = SatelliteDAO.find { SatellitesTable.noradCatId eq id }
                if(!temp.empty()) {
                    selectedSat = SatelliteDAO.find { SatellitesTable.noradCatId eq id }.first()
                }
            }
            if(selectedSat == null) {
                call.respondRedirect("/satellites")
            }

            call.respondHtmlTemplate(LayoutTemplate()) {
                route = WebRoutesEnum.view_satellite.route

                header {
                    +selectedSat!!.objectName!!
                }

                this.viewSatelliteContent {
                    sat = selectedSat as SatelliteDAO
                }
            }
        }
    }

    accountWebRouting()
}

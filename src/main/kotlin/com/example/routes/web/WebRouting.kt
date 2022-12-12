package com.example.routes.web

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import com.example.templates.LayoutTemplate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.webRouting() {
    route("/") {
        get("/") {
            call.respondRedirect("/home")
        }

        get(WebRoutesEnum.home.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  WebRoutesEnum.home.route
            }
        }

        get(WebRoutesEnum.create_account.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  WebRoutesEnum.create_account.route
            }
        }

        get(WebRoutesEnum.satellites.toString()) {
            var requestedPage : Int? = null

            if(!call.request.queryParameters["page"].isNullOrBlank()) {
                requestedPage = call.request.queryParameters["page"]!!.toInt()
                if(requestedPage.toInt() > 50)
                    call.respondRedirect("/satellites?page=50")
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
        }
        get(WebRoutesEnum.view_satellite.toString()) {
            var id  = ""
            var selectedSat : SatelliteDAO? = null

            if(call.request.queryParameters["id"].isNullOrBlank() || call.request.queryParameters["id"] == "") {
                call.respondRedirect("/satellites")
            }
                id = call.request.queryParameters["id"]!!

                transaction {

                    val temp = SatelliteDAO.find { Satellites.noradCatId eq id }
                    if(!temp.empty()) {
                        selectedSat = SatelliteDAO.find { Satellites.noradCatId eq id }.first()
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
}
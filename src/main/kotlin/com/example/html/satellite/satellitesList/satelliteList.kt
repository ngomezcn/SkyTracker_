package com.example.templates.content

import com.example.orm.tables.SatelliteDAO
import com.example.orm.tables.SatellitesTable
import com.example.html.satellite.satellitesList.earthViewer
import com.example.html.satellite.satellitesList.ulSatelliteList
import com.example.newRoutes.Satellites
import io.ktor.server.application.*
import io.ktor.server.resources.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun FlowContent.satellitesList(application: Application, sats: List<SatelliteDAO>) {

            div("overlay") {
            id = "loading"
            div {
                div("text-center ") {
                    div("spinner-border") {
                        style = "width: 3rem; height: 3rem; color: #0D6EFD"
                        role = "status"
                    }
                }
            }
        }

        div("row no-gutters") {

            div("col-12 col-sm-6 col-md-8") {

                earthViewer()
            }

            div("col-6 col-md-4") {
                id = "satListPanel"
                style="opacity: 0;"
                div("panel-heading") {
                    id="sat_list_heading"
                    h3("panel-title") { +"""Result List""" }
                    +"""Panel para buscar"""
                }
                nav {
                    ul("pagination") {
                       /* if(page!!-1 > 0) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = application.href(Satellites(page = page - 1))
                                    tabIndex = "-1"
                                    +"""Previous"""
                                }
                            }
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = application.href(Satellites(page = page - 1))
                                    +"${page!!-1}"
                                }
                            }
                        } else
                        {
                            li("page-item disabled") {
                                a(classes = "page-link") {
                                    href = application.href(Satellites(page = page - 1))
                                    tabIndex = "-1"
                                    +"""Previous"""
                                }
                            }
                        }
                        li("page-item active") {
                            a(classes = "page-link") {
                                href = "#"
                                +"${page!!}"
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = application.href(Satellites(page = page + 1))
                                +"${page!!+1}"
                            }
                        }
                        if(page!!-1 <= 0) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = application.href(Satellites(page = page + 1))
                                    +"${page!!+2}"
                                }
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = application.href(Satellites(page = page + 1))
                                +"""Next"""
                            }
                        }*/
                    }
                }
               ulSatelliteList(application, sats)
            }
        }

        script {
            unsafe {
                +"const list = document.getElementById(\"sat_list\"); list.style.maxHeight = (window.innerHeight-72-66)+'px';"
            }
        }
}

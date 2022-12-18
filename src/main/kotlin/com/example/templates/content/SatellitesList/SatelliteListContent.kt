package com.example.templates.content

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.SatellitesTable
import com.example.templates.content.SatellitesList.EarthViewer
import com.example.templates.content.SatellitesList.UlSatelliteList
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SatelliteListContent : Template<FlowContent> {
    private val earthViewer = TemplatePlaceholder<EarthViewer>()
    private val satelliteList = TemplatePlaceholder<UlSatelliteList>()

    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    private val itemsXPage: Long = 5000; // of 42000 aprox

    override fun FlowContent.apply() {

        if (page == null)
            page = 1

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dt = LocalDate.parse("1958-03-17", formatter)

        transaction {
            sats = SatelliteDAO.find { SatellitesTable.decayDate eq null }.toList().sortedByDescending {

                if(it.launchDate.isNullOrEmpty()) {
                    LocalDate.parse("1899-03-17", formatter)
                } else
                {
                    LocalDate.parse(it.launchDate, formatter)
                }
            }.slice((page!! * itemsXPage).toInt()..(page!! * itemsXPage+itemsXPage).toInt())
                /*.sortedByDescending {
                LocalDateTime.parse(text, pattern)
            }*/

                //.sortedByDescending { it.noradCatId.toInt() }
                //.slice((page!! * itemsXPage).toInt()..(page!! * itemsXPage+itemsXPage).toInt())
        }

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

                insert(EarthViewer(), earthViewer)
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
                        if(page!!-1 > 0) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "/satellites?page=" + (page!! - 1)
                                    tabIndex = "-1"
                                    +"""Previous"""
                                }
                            }
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "/satellites?page=" + (page!! - 1)
                                    +"${page!!-1}"
                                }
                            }
                        } else
                        {
                            li("page-item disabled") {
                                a(classes = "page-link") {
                                    href = "/satellites?page=" + (page!! - 1)
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
                                href = "/satellites?page=" + (page!! + 1)
                                +"${page!!+1}"
                            }
                        }
                        if(page!!-1 <= 0) {
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "/satellites?page=" + (page!! + 2)
                                    +"${page!!+2}"
                                }
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! + 1)
                                +"""Next"""
                            }
                        }
                    }
                }
                insert(UlSatelliteList(sats), satelliteList)
            }
        }

        section("py-5 text-center") {
            nav {
                ul("pagination") {
                    if(page!!-1 > 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                +"${page!!-1}"
                            }
                        }
                    } else
                    {
                        li("page-item disabled") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
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
                            href = "/satellites?page=" + (page!! + 1)
                            +"${page!!+1}"
                        }
                    }
                    if(page!!-1 <= 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! + 2)
                                +"${page!!+2}"
                            }
                        }
                    }
                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"""Next"""
                        }
                    }
                }
            }
        }

        script {
            unsafe {
                +"const list = document.getElementById(\"sat_list\"); list.style.maxHeight = (window.innerHeight-72-66)+'px';"
            }
        }
    }
}

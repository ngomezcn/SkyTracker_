package com.example.templates.content

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.transactions.transaction

class SatelliteListContent : Template<FlowContent> {
    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage: Long = 3*6;
        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.limit(itemsXPage.toInt(), itemsXPage * page!! - itemsXPage).toList()
        }

        div("container px-5 card-columns") {
            for (sat in sats) {

                div("card") {
                    onClick = "window.location = '/view_satellite?id=${sat.noradCatId}';"

                    style = "width: 18rem;"
                    img(classes = "card-img-top") {
                        src =
                            "https://www.heavens-above.com/orbitdisplay.aspx?icon=iss&width=300&height=300&mode=A&satid=${sat.noradCatId}"
                        alt = "Card image cap"
                    }
                    div("card-block px-2") {
                        if(sat.objectName!!.length > 16)
                            h4("card-title pt-lg-2") { +"${sat.objectName!!.take(16)}..." }
                        else
                            h4("card-title pt-lg-2") { +"${sat.objectName}" }

                    }
                    div("card-footer w-100 text-muted") {
                        p("card-subtitle") { +"""Launch date: "${sat.launchDate}"""" }
                        p("card-subtitle") { +"""Norad ID: "${sat.noradCatId}"""" }
                        p("card-subtitle") { +"""Sat identifier: "${sat.objectID}"""" }
                    }
                }
                br { }

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
                            span("sr-only") { +"""(current)""" }
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
    }
}

class ListItem : Template<FlowContent> {
    override fun FlowContent.apply() {
        TODO("Not yet implemented")
    }


}
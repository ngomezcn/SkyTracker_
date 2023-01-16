package com.example.templates.content.ViewSatellite

import com.example.loggedUser
import com.example.orm.models.*
import com.example.pathAssetsSats
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


class ViewSatelliteContent : Template<FlowContent> {
    lateinit var sat: SatelliteDAO

    override fun FlowContent.apply() {

        var comments : List<SatCommentDAO> = listOf()
        transaction {

            val query = SatCommentTable.innerJoin(SatellitesTable)
                .slice(SatCommentTable.columns)
                .select {
                    (SatellitesTable.id eq SatCommentTable.idSatellite) and (SatellitesTable.id eq sat.id)
                }.withDistinct()

            if (SatCommentDAO.wrapRows(query).count().toInt() > 0) {
                comments = SatCommentDAO.wrapRows(query).toList()
            }
        }

        p { +sat.objectName!! }
        p { +sat.noradCatId!! }


        var b = UserTrackingSatDAO.isSatTrackedByUser(satId = sat.id, userId = loggedUser!!.id)

        if(b == null)
        {
            form {
                div("form-group") {
                    button(classes = "btn btn-primary mb-2") {
                        type = ButtonType.reset
                        +"""Tracked"""
                    }
                }
            }
        } else
        {
            form {
                action = "api/track_satellite"
                method = FormMethod.post
                encType = FormEncType.multipartFormData

                input {
                    type = InputType.hidden
                    name = "idSatellite"
                    value = "${sat.id}"
                }
                div("form-group") {
                    button(classes = "btn btn-primary mb-2") {
                        type = ButtonType.submit
                        +"""Track satellite """
                    }
                }
            }
        }
        section("py-5") {
            form {
                action = "api/post_message"
                method = FormMethod.post
                encType = FormEncType.multipartFormData

                div("container px-5") {
                    div("bg-light rounded-3 py-5 px-4 px-md-5 mb-5") {
                        div("form-group") {
                            label {
                                htmlFor = "message"
                                +"""Comment something"""
                            }
                            textArea(classes = "form-control") {
                                name = "message"
                                rows = "5"
                            }
                        }
                        div("form-outline ") {
                            label {
                                htmlFor = "image"
                                +"""Upload your image"""

                            }
                            br {  }
                            input(classes = "form-control-file ") {
                                placeholder="Do you want to share something?"
                                type = InputType.file
                                name = "image"
                            }

                        }
                        br {  }
                        input {
                            type = InputType.hidden
                            name = "idSatellite"
                            value = "${sat.id}"
                        }
                        div("form-group") {
                            button(classes = "btn btn-primary mb-2") {
                                type = ButtonType.submit
                                +"""Comment"""
                            }
                        }
                    }
                }
            }
        }
        val path = "assets/sats/"

        for (c in comments) {
            p { + "${c.comment}" }

            if(c.imageName.isNotBlank()) {
                img {
                    src = path + c.imageName
                    height = "200px"
                }
            }
        }
    }
}

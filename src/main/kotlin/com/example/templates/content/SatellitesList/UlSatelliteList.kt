package com.example.templates.content.SatellitesList

import com.example.orm.models.SatelliteDAO
import io.ktor.server.html.*
import kotlinx.html.*

class UlSatelliteList(private val sats: List<SatelliteDAO>): Template<FlowContent> {

    override fun FlowContent.apply() {
        link(rel = "stylesheet", href = "css/satList.css", type = "text/css")

        div("panel panel-primary ") {
            id = "satListPanel"
            style="opacity: 0;"
            div("panel-heading") {
                id="sat_list_heading"
                h3("panel-title") { +"""Result List""" }
                +"""Panel para buscar"""
            }
            div("panel-body bg-primary") {

                ul("list-group bg-primary") {
                    id="sat_list"
                    for (sat in sats) {
                        li("list-group-item") {
                            onMouseOver="console.log('${sat.objectName} - ${sat.noradCatId}'); ui.SendMessage('GameManager', 'selectSat', '${sat.tleLine1!!.subSequence(9, 15)}');"
                            id="${sat.tleLine1!!.subSequence(9, 15)}"
                            a(classes = "list-group-item list-group-item-action list-group-item-secondary") {
                                href = "/view_satellite?id=${sat.noradCatId}"
                                strong { +"${sat.objectName}" }
                                br {
                                }
                                strong { +"""Launch date:""" }
                                +"${sat.launchDate}"
                                strong { +""" Norad ID:""" }
                                +"${sat.noradCatId}"
                            }
                            script {
                                unsafe {
                                    +"function test1() { ui.SendMessage('GameManager', 'addSat', JSON.stringify({ tle1: \"${sat.tleLine1}\", tle2: \"${sat.tleLine2}\" })) } setTimeout(test1, 3500);"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

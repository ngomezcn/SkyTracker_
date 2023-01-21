package com.example.html.satellite.satellitesList

import com.example.newRoutes.Satellites
import com.example.orm.tables.SatelliteDAO
import io.ktor.server.application.*
import io.ktor.server.resources.*
import kotlinx.html.*

fun FlowContent.ulSatelliteList(application : Application, sats: List<SatelliteDAO>) {

        link(rel = "stylesheet", href = "/css/satList.css", type = "text/css")

        div("panel panel-primary ") {

            div("panel-body bg-primary") {

                ul("list-group bg-primary") {
                    id="sat_list"
                    for (sat in sats) {
                        li("list-group-item") {
                            onMouseOver="console.log('${sat.objectName} - ${sat.noradCatId}'); ui.SendMessage('GameManager', 'selectSat', '${sat.tleLine1!!.subSequence(9, 15)}');"
                            id="${sat.tleLine1!!.subSequence(9, 15)}"
                            a(classes = "list-group-item list-group-item-action list-group-item-secondary") {
                                href =  application.href(Satellites.NoradId(noradId = sat.noradCatId))
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

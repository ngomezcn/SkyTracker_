package com.example.templates.content

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import com.example.templates.content.SatellitesList.EarthViewer
import com.example.templates.content.SatellitesList.UlSatelliteList
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction

class SatelliteListContent : Template<FlowContent> {
    private val earthViewer = TemplatePlaceholder<EarthViewer>()
    private val satelliteList = TemplatePlaceholder<UlSatelliteList>()

    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    private val itemsXPage: Long = 5000; // of 42000 aprox

    override fun FlowContent.apply() {

        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.toList().sortedByDescending { it.noradCatId.toInt() }
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
                insert(UlSatelliteList(sats), satelliteList)
            }
        }

        script {
            unsafe {
                +"const list = document.getElementById(\"sat_list\"); list.style.maxHeight = (window.innerHeight-72-66)+'px';"
            }
        }
    }
}

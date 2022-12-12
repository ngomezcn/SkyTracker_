package com.example.templates.content

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.orm.models.SatelliteDAO
import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.header
import kotlinx.html.p

class ViewSatelliteContent: Template<FlowContent> {
    lateinit var sat : SatelliteDAO

    override fun FlowContent.apply() {

        p{
            +sat.objectName!!
        }
        p{
            +sat.noradCatId!!
        }
    }
}


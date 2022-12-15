package com.example.templates.content.SatellitesList

import io.ktor.server.html.*
import kotlinx.html.*

class EarthViewer : Template<FlowContent> {
    override fun FlowContent.apply() {
        div("unity-desktop") {
            id = "unity-container"
            canvas {
                id = "unity-canvas"

            }
            div {
                id = "unity-loading-bar"
                div {
                    id = "unity-logo"
                }
                div {
                    id = "unity-progress-bar-empty"
                    div {
                        id = "unity-progress-bar-full"
                    }
                }
            }
            script {
                src = "js/scriptBuildOrbits.js"
            }
        }
    }
}

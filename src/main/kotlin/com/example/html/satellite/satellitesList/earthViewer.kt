package com.example.html.satellite.satellitesList

import kotlinx.html.*


fun FlowContent.earthViewer() {
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
                src = "/js/scriptBuildOrbits.js"
            }
        }
}

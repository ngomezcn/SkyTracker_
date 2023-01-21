package com.example.html


import com.example.html.layout.footer
import com.example.html.layout.navigation
import com.example.templates.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*

class AppLayout(private val application: Application): Template<HTML> {

    val header = Placeholder<FlowContent>()
    var route : String = ""

    // Pages content
    // Default structure

    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {

        head {
            meta {
                charset = "utf-8"
            }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1, shrink-to-fit=no"
            }
            link {
                rel = "icon"
                type = "image/x-icon"
                href = "/assets/favicon.ico"
            }
            link {
                href = "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
                rel = "stylesheet"
            }
            link {
                href = "/css/styles.css"
                rel = "stylesheet"
            }
            link {
                rel = "stylesheet"
                href = "https://maxcdn.bootstrapcdn.com/bootstrap/5.0.0-alpha.2/css/bootstrap.min.css"
                attributes["integrity"] = "sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd"
                attributes["crossorigin"] = "anonymous"
            }
            title {
                +"${route.uppercase()}"
            }
        }

        body {
            onLoad="onLoadDelayed()"


            navigation(application)
            insert(content)

            script {
                src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            }
        }
    }
}




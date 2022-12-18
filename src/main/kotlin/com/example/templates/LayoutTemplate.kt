package com.example.templates

import com.example.routes.web.WebRoutesEnum
import com.example.templates.content.*
import com.example.templates.content.ViewSatellite.ViewSatelliteContent
import io.ktor.server.html.*
import kotlinx.html.*
import java.io.IOException


class LayoutTemplate: Template<HTML> {

    val header = Placeholder<FlowContent>()
    var route : String = ""

    // Pages content
    val satelliteListContent = TemplatePlaceholder<SatelliteListContent>()
    val viewSatelliteContent = TemplatePlaceholder<ViewSatelliteContent>()
    val createAccountContent = TemplatePlaceholder<CreateAccountContent>()
    val signInContent = TemplatePlaceholder<SignInContent>()
    val homeContent = TemplatePlaceholder<HomeContent>()

    // Default structure
    private val navigationContent = TemplatePlaceholder<NavigationContent>()
    private val footerContent = TemplatePlaceholder<FooterContent>()

    override fun HTML.apply() {

        if(route == "") {
            throw IOException("No se ha indicado la ruta")
        }

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
                href = "assets/favicon.ico"
            }
            link {
                href = "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
                rel = "stylesheet"
            }
            link {
                href = "css/styles.css"
                rel = "stylesheet"
            }
            link {
                rel = "stylesheet"
                href = "https://maxcdn.bootstrapcdn.com/bootstrap/5.0.0-alpha.2/css/bootstrap.min.css"
                attributes["integrity"] = "sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd"
                attributes["crossorigin"] = "anonymous"
            }
            title {
                +"${route}"
            }
        }

        body {
            onLoad="onLoadDelayed()"
            insert(NavigationContent(), navigationContent)
            when(route) {

                WebRoutesEnum.home.route -> insert(HomeContent(), homeContent)
                WebRoutesEnum.satellites.route -> insert(SatelliteListContent(), satelliteListContent)
                WebRoutesEnum.view_satellite.route -> insert(ViewSatelliteContent(), viewSatelliteContent)
                WebRoutesEnum.create_account.route -> insert(CreateAccountContent(), createAccountContent)
                WebRoutesEnum.sign_in.route -> insert(SignInContent(), signInContent)
            }
            if(route != WebRoutesEnum.satellites.route) {
                //insert(FooterContent(), footerContent)
            }
            script {
                src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            }
        }
    }
}




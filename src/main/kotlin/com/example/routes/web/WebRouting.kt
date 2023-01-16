package com.example.routes.web

import com.example.routes.web.account.AccountWebRouting
import com.example.routes.web.satellites.SatelliteRouting
import com.example.templates.LayoutTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.webRouting() {
    route("/") {
        get("/") {
            call.respondRedirect("/home")
        }

        get(WebRoutesEnum.home.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  WebRoutesEnum.home.route
            }
        }

        get(WebRoutesEnum.create_account.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  WebRoutesEnum.create_account.route
            }
        }

        get(WebRoutesEnum.sign_in.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  WebRoutesEnum.sign_in.route
            }
        }
    }

    SatelliteRouting()
    AccountWebRouting()
}
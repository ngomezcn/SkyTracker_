package com.example.routes.web.account

import com.example.templates.LayoutTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*

fun Route.AccountWebRouting() {
    route("/account/") {
        get(AccountRoutesEnum.tracking_list.toString()) {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route =  AccountRoutesEnum.tracking_list.route
            }
        }
    }
}
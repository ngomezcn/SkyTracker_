package com.example.plugins

import com.example.models.login.customerRouting
import com.example.routes.api.apiRouting
import com.example.routes.web.webRouting
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import java.io.File

fun Application.configureRouting() {

    routing {
        resource("/", "index.html")
        resource("*", "index.html")

        static("/") {
            resources("static")
        }

        apiRouting()
        webRouting()
        customerRouting()
    }
}

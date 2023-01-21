package com.example.plugins

import com.example.newRoutes.accountRoutes
import com.example.newRoutes.rootRoutes
import com.example.newRoutes.satelliteRoutes
import com.example.routes.api.apiRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.openapi.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import io.swagger.codegen.v3.generators.kotlin.KotlinServerCodegen
import java.io.File
import java.io.InputStream

fun Application.configureRouting() {

    routing {
        println("Request")
        //resource("/", "index.html")
        //resource("*", "index.html")

        static("/") {
            resources("static")
        }

        //openAPI(path="openapi", swaggerFile = "D:\\GitRepos\\SkyTracker_\\src\\main\\resources\\static\\openapi/documentation.yaml")
        //swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        //apiRouting()
        //webRouting()

        // New routing
        rootRoutes()
        accountRoutes()
        satelliteRoutes()
    }
}

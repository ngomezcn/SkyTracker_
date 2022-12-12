package com.example.routes.api

import com.example.models.login.CreateAccountModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.apiRouting() {
    route("api") {
        post("create_account") {
            println("api/create_account")
            println(Json.encodeToString(CreateAccountModel("ngomez","Naim","Gomez","naim.gomez.7e5@itb.cat","1234")))

            try {
                val recv = call.receive<CreateAccountModel>()
                println("new: "+recv)
            } catch  (e: Exception) {

                println(e)
            }

            //val serializer = Json{
            //    ignoreUnknownKeys = true
            // }
            // val a = serializer.decodeFromString<CreateAccount>(recv.trimIndent())
            //println(a)
            call.respondText("User created correctly", status = HttpStatusCode.Created)
        }

    }
}
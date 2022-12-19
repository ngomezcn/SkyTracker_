package com.example.routes.api

import com.example.loggedUser
import com.example.orm.models.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
@Resource("tracking_list")
class TrackingList(){
    @Serializable
    @Resource("new")
    class New(val parent: TrackingList = TrackingList())

    @Serializable
    @Resource("add")
    class Add(val parent: TrackingList = TrackingList())
}

fun Route.TrackingList() {
    route("api") {
        post("track_satellite") {

            val data = call.receiveMultipart()

            var email = ""
            var password = ""

            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        transaction {
                            when (part.name){
                                "email" -> email = part.value
                                "password" -> password = part.value
                            }
                        }
                    }
                    is PartData.FileItem -> {                    }
                    else -> {
                    }
                }
            }



            transaction {
                addLogger(StdOutSqlLogger)

                val query = UsersTable.select {
                    (UsersTable.email eq email) and (UsersTable.password eq password)
                }

                if(UserDAO.wrapRows(query).count().toInt() > 0) {
                    loggedUser =  UserDAO.wrapRows(query).toList().first()
                }
            }

            if(loggedUser == null) {
                call.respondRedirect("/sign_in?errorMsg=\"Datos incorrectos\"")
            } else
            {
                call.respondRedirect("/satellites")
            }

        }
    }
}
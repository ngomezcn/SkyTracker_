package com.example.newRoutes

import com.example.html.AppLayout
import com.example.html.layout.navigation
import com.example.html.root.home
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
@Resource("/")
class Root(){
    @Serializable
    @Resource("home")
    class Home(val parent: Root = Root())
}

fun Route.rootRoutes() {

    get<Root.Home> {
        call.respondHtmlTemplate(AppLayout(application)) {
            content {
                transaction {
                    home()
                }
            }
        }
    }
}

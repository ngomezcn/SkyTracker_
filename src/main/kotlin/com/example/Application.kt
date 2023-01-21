package com.example

import com.example.newRoutes.Satellites
import com.example.orm.ORM
import com.example.orm.modelsoSatellite.UserDAO
import com.example.orm.modelsoSatellite.UsersTable

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.nio.file.Paths
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*

/*
select T0.username, count(*)
from users T0
inner join satcomment T1
on T0.id = T1."idUser"
where T0.email = 'naimgomezcn@gmail.com'
group by T0.username
*/

var loggedUser : UserDAO? = null

val basePath  = File(ORM::class.java.protectionDomain.codeSource.location.path).path!!
val pathAssetsSats = Paths.get(basePath).parent.parent.parent.combineSafe(Paths.get("resources/main/static/assets/sats"))
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

   ORM.connect()
    ORM.createSchemas()
    ORM.loadInitialData()

    // ESTO ES SOLO TEMPORAL DURANTE EL DESARROLLO
    transaction {
        addLogger(StdOutSqlLogger)

        val query = UsersTable.select {
            (UsersTable.email eq "naimgomezcn@gmail.com") and (UsersTable.password eq "1234")
        }

        if(UserDAO.wrapRows(query).count().toInt() > 0) {
            loggedUser =  UserDAO.wrapRows(query).toList().first()
        }
    }
    // === == == == == =

    install(CORS)
    install(Resources)
    configureSerialization()
    configureRouting()
}

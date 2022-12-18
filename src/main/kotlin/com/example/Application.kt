package com.example

import com.example.orm.ORM
import com.example.orm.models.UserDAO
import com.example.orm.models.UsersTable
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

var loggedUser : UserDAO? = null

fun main() {
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

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}

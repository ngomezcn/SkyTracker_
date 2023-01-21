package com.example.newRoutes

import com.example.html.AppLayout
import com.example.html.account.createAccount
import com.example.html.account.signIn
import com.example.html.account.trackingList
import com.example.loggedUser
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable

@Serializable
@Resource("account")
class Account(){
    @Serializable
    @Resource("tracking_list")
    class TrackingList(val parent: Account = Account())

    @Serializable
    @Resource("signIn")
    class SignIn(val parent: Account = Account())

    @Serializable
    @Resource("create_account")
    class CreateAccount(val parent: Account = Account())

    @Serializable
    @Resource("logout")
    class Logout(val parent: Account = Account())
}

fun Route.accountRoutes() {
    //val accountRepository = FilmsRepository()

    get<Account.TrackingList> {
        call.respondHtmlTemplate(AppLayout(application)) {
            content{
                transaction {
                    trackingList()
                }
            }
        }
    }

    get<Account.SignIn> {
        call.respondHtmlTemplate(AppLayout(application)) {
            content{
                transaction {
                    signIn()
                }
            }
        }
    }

    get<Account.CreateAccount> {
        call.respondHtmlTemplate(AppLayout(application)) {
            content{
                transaction {
                    createAccount()
                }
            }
        }
    }

    get<Account.Logout> {
        loggedUser = null
        call.respondRedirect("/sign_in")
    }
}

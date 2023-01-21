package com.example.html.layout

import com.example.loggedUser
import com.example.newRoutes.Account
import com.example.newRoutes.Satellites
import io.ktor.server.application.*
import io.ktor.server.resources.*
import kotlinx.html.*

fun FlowContent.navigation(application: Application) {

        nav("navbar navbar-expand-lg navbar-dark bg-dark") {
            div("container px-5") {
                a(classes = "navbar-brand") {
                    href = "/"
                    +"""SkyTracker"""
                }
                button(classes = "navbar-toggler") {
                    type = ButtonType.button
                    attributes["data-bs-toggle"] = "collapse"
                    attributes["data-bs-target"] = "#navbarSupportedContent"
                    attributes["aria-controls"] = "navbarSupportedContent"
                    attributes["aria-expanded"] = "false"
                    attributes["aria-label"] = "Toggle navigation"
                    span("navbar-toggler-icon") {
                    }
                }
                div("collapse navbar-collapse") {
                    id = "navbarSupportedContent"
                    ul("navbar-nav ms-auto mb-2 mb-lg-0") {
                        li("nav-item") {
                            a(classes = "nav-link") {
                                href = "/"
                                +"""Home"""
                            }
                        }
                        li("nav-item") {
                            a(classes = "nav-link") {
                                href = application.href(Satellites())
                                +"""Satellites"""
                            }
                        }
                        li("nav-item") {
                            a(classes = "nav-link") {
                                href = "api"
                                +"""API"""
                            }
                        }

                        li("nav-item dropdown") {

                            a(classes = "nav-link dropdown-toggle") {
                                id = "navbarDropdownBlog"
                                href = "#"
                                role = "button"
                                attributes["data-bs-toggle"] = "dropdown"
                                attributes["aria-expanded"] = "false"
                                +"""Account"""
                            }
                            ul("dropdown-menu dropdown-menu-end") {
                                attributes["aria-labelledby"] = "navbarDropdownBlog"
                                if(loggedUser != null)
                                {
                                    li {
                                        p(classes = "dropdown-item") {
                                            +"""${loggedUser?.email}"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = application.href(Account.TrackingList())
                                            +"""My tracking list"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "#"
                                            +"""Statistics"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = application.href(Account.Logout())
                                            +"""Log out"""
                                        }
                                    }
                                } else
                                {
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = application.href(Account.SignIn())
                                            +"""Sign In"""
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}

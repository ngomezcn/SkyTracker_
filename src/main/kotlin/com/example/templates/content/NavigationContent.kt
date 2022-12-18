package com.example.templates.content

import com.example.loggedUser
import io.ktor.server.html.*
import kotlinx.html.*

class NavigationContent: Template<FlowContent> {
    override fun FlowContent.apply() {

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
                                href = "satellites"
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
                                        a(classes = "dropdown-item") {
                                            +"""${loggedUser!!.surname}"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "account/tracking_list"
                                            +"""My tracking list"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "account/settings"
                                            +"""Settings"""
                                        }
                                    }
                                } else
                                {
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "sign_in"
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


}

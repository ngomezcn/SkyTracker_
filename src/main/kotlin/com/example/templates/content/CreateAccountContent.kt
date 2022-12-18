package com.example.templates.content

import io.ktor.http.*
import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.*

class CreateAccountContent: Template<FlowContent> {
    override fun FlowContent.apply() {

        section("py-5") {
            div("container px-5") {
                div("bg-light rounded-3 py-5 px-4 px-md-5 mb-5") {
                    div("text-center mb-5") {
                        div("feature bg-primary bg-gradient text-white rounded-3 mb-3") {
                            i("bi bi-envelope") {
                            }
                        }
                        h1("fw-bolder") { +"""Create Account""" }
                        p("lead fw-normal text-muted mb-0") {
                            +"""Already have an account?  """
                            a(classes = "link-secondary") {
                                href = "/sign_in"
                                +"""Log In"""
                            }
                        }
                    }
                    div("row gx-5 justify-content-center") {
                        div("col-lg-8 col-xl-6") {
                            form {
                                action = "api/create_account"
                                method = FormMethod.post
                                encType = FormEncType.multipartFormData

                                div("form-floating mb-3") {
                                    input(classes = "form-control") {
                                        name = "username"
                                        type = InputType.text
                                        placeholder = "Username"
                                    }
                                    label {
                                        htmlFor = "username"
                                        +"""Username"""
                                    }
                                }
                                div("form-floating mb-3") {
                                    input(classes = "form-control") {
                                        name = "firstName"
                                        type = InputType.text
                                        placeholder = "First name"
                                    }
                                    label {
                                        htmlFor = "firstName"
                                        +"""First name"""
                                    }
                                }
                                div("form-floating mb-3") {
                                    input(classes = "form-control") {
                                        name = "surname"
                                        type = InputType.text
                                        placeholder = "Surname"
                                    }
                                    label {
                                        htmlFor = "surname"
                                        +"""Surname"""
                                    }
                                }
                                div("form-floating mb-3") {
                                    input(classes = "form-control") {
                                        name = "email"
                                        type = InputType.email
                                        placeholder = "Email address"
                                    }
                                    label {
                                        htmlFor = "email"
                                        +"""Email address"""
                                    }
                                }
                                div("form-floating mb-3") {
                                    input(classes = "form-control") {
                                        name = "password"
                                        type = InputType.password
                                        placeholder = "Password"
                                    }
                                    label {
                                        htmlFor = "password"
                                        +"""Password"""
                                    }
                                }
                                div("d-grid") {
                                    button(classes = "btn btn-primary btn-lg") {
                                        type = ButtonType.submit
                                        +"""Submit"""
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
package com.example.html.layout

import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.footer
import kotlinx.html.*

fun FlowContent.footer() {

    footer("bg-dark py-4 mt-auto")
        {
            div("container px-5") {
                div("row align-items-center justify-content-between flex-column flex-sm-row") {
                    div("col-auto") {
                        div("small m-0 text-white") { +"""Copyright © SkyTracker 2022""" }
                    }
                    div("col-auto") {
                        a(classes = "link-light small") {
                            href = "#!"
                            +"""Privacy"""
                        }
                        span("text-white mx-1") { +"""·""" }
                        a(classes = "link-light small") {
                            href = "#!"
                            +"""Terms"""
                        }
                        span("text-white mx-1") { +"""·""" }
                        a(classes = "link-light small") {
                            href = "#!"
                            +"""Contact"""
                    }
                }
            }
        }
    }
}
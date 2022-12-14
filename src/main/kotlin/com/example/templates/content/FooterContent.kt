package com.example.templates.content

import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.footer
import kotlinx.html.*

class FooterContent: Template<FlowContent> {
    override fun FlowContent.apply() {
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
        script {
            src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        }

    }
}
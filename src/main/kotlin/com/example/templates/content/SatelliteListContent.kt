package com.example.templates.content

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.transactions.transaction

/*class SatelliteListContent : Template<FlowContent> {
    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage: Long = 3*3;
        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.limit(itemsXPage.toInt(), itemsXPage * page!! - itemsXPage).toList()
        }

        div("unity-desktop") {
            id = "unity-container"
            canvas {
                id = "unity-canvas"
                width = "960"
                height = "600"
            }
            div {
                id = "unity-loading-bar"
                div {
                    id = "unity-logo"
                }
                div {
                    id = "unity-progress-bar-empty"
                    div {
                        id = "unity-progress-bar-full"
                    }
                }
            }
        }
        script {
            src = "js/scriptBuildOrbits.js"
        }
        script {
            src = "js/scripts.js"
        }
        div("container px-5 card-columns") {
            for (sat in sats) {

                div("card") {
                    onClick = "window.location = '/view_satellite?id=${sat.noradCatId}';"

                    style = "width: 15rem;"
                    img(classes = "card-img-top") {
                        src =
                            "https://www.heavens-above.com/orbitdisplay.aspx?icon=iss&width=300&height=300&mode=A&satid=${sat.noradCatId}"
                        alt = "Card image cap"
                    }
                    div("card-block px-2") {
                        if(sat.objectName!!.length > 16)
                            h4("card-title pt-lg-2") { +"${sat.objectName!!.take(16)}..." }
                        else
                            h4("card-title pt-lg-2") { +"${sat.objectName}" }

                    }
                    div("card-footer w-100 text-muted") {
                        p("card-subtitle") { +"""Launch date: "${sat.launchDate}"""" }
                        p("card-subtitle") { +"""Norad ID: "${sat.noradCatId}"""" }
                        p("card-subtitle") { +"""Sat identifier: "${sat.objectID}"""" }
                    }
                }
                br { }

                script {
                    unsafe {
                        +"function test1() { u.SendMessage('GameManager', 'addSat', JSON.stringify({ tle1: \"${sat.tleLine1}\", tle2: \"${sat.tleLine2}\" })) } setTimeout(test1, 3000);"
                    }
                }

            }
        }

        section("py-5 text-center") {
            nav {
                ul("pagination") {

                    if(page!!-1 > 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                +"${page!!-1}"
                            }
                        }
                    } else
                    {
                        li("page-item disabled") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                    }

                    li("page-item active") {
                        a(classes = "page-link") {
                            href = "#"
                            +"${page!!}"
                            span("sr-only") { +"""(current)""" }
                        }
                    }
                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"${page!!+1}"
                        }
                    }

                    if(page!!-1 <= 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! + 2)
                                +"${page!!+2}"
                            }
                        }
                    }


                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"""Next"""
                        }
                    }
                }
            }
        }


    }
}*/

class SatelliteListContent : Template<FlowContent> {
    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage: Long = 3*1000;
        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.limit(itemsXPage.toInt(), itemsXPage * page!! - itemsXPage).toList()
        }

        link(rel = "stylesheet", href = "css/satList.css", type = "text/css")

        div("row no-gutters") {
            div("col-12 col-sm-6 col-md-8") {
                div("unity-desktop") {
                    id = "unity-container"
                   canvas {
                        id = "unity-canvas"

                    }
                    div {
                        id = "unity-loading-bar"
                        div {
                            id = "unity-logo"
                        }
                        div {
                            id = "unity-progress-bar-empty"
                            div {
                                id = "unity-progress-bar-full"
                            }
                        }
                    }
                    script {
                        src = "js/scriptBuildOrbits.js"
                    }
                }
            }

            div("col-6 col-md-4") {
                div("panel panel-primary ") {
                    id = "result_panel"
                    div("panel-heading") {
                        id="sat_list_heading"
                        h3("panel-title") { +"""Result List""" }
                        +"""Panel para buscar"""
                    }
                    div("panel-body bg-primary") {
                        ul("list-group bg-primary") {
                            id="sat_list"
                            for (sat in sats) {
                                li("list-group-item") {
                                    onClick = "window.location = '/view_satellite?id=${sat.noradCatId}';"
                                    a(classes = "list-group-item list-group-item-action list-group-item-secondary") {
                                        href = "#"
                                        strong { +"${sat.objectName}" }
                                        br {
                                        }
                                        strong { +"""Launch date:""" }
                                        +"${sat.launchDate}"
                                        strong { +""" Norad ID:""" }
                                        +"${sat.noradCatId}"
                                    }
                                    script {
                                        unsafe {
                                            +"function test1() { ui.SendMessage('GameManager', 'addSat', JSON.stringify({ tle1: \"${sat.tleLine1}\", tle2: \"${sat.tleLine2}\" })) } setTimeout(test1, 3500);"
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        script {
            src = "js/scripts.js"
        }
    }
}


/*class SatelliteListContent : Template<FlowContent> {
    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage: Long = 3*3;
        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.limit(itemsXPage.toInt(), itemsXPage * page!! - itemsXPage).toList()
        }

        div("unity-desktop") {
            id = "unity-container"
            canvas {
                id = "unity-canvas"
                width = "960"
                height = "600"
            }
            div {
                id = "unity-loading-bar"
                div {
                    id = "unity-logo"
                }
                div {
                    id = "unity-progress-bar-empty"
                    div {
                        id = "unity-progress-bar-full"
                    }
                }
            }
        }
        script {
            src = "js/scriptBuildOrbits.js"
        }
        script {
            src = "js/scripts.js"
        }
        div("container px-5 card-columns") {
            for (sat in sats) {

                div("card") {
                    onClick = "window.location = '/view_satellite?id=${sat.noradCatId}';"

                    style = "width: 15rem;"
                    img(classes = "card-img-top") {
                        src =
                            "https://www.heavens-above.com/orbitdisplay.aspx?icon=iss&width=300&height=300&mode=A&satid=${sat.noradCatId}"
                        alt = "Card image cap"
                    }
                    div("card-block px-2") {
                        if(sat.objectName!!.length > 16)
                            h4("card-title pt-lg-2") { +"${sat.objectName!!.take(16)}..." }
                        else
                            h4("card-title pt-lg-2") { +"${sat.objectName}" }

                    }
                    div("card-footer w-100 text-muted") {
                        p("card-subtitle") { +"""Launch date: "${sat.launchDate}"""" }
                        p("card-subtitle") { +"""Norad ID: "${sat.noradCatId}"""" }
                        p("card-subtitle") { +"""Sat identifier: "${sat.objectID}"""" }
                    }
                }
                br { }

                script {
                    unsafe {
                        +"function test1() { u.SendMessage('GameManager', 'addSat', JSON.stringify({ tle1: \"${sat.tleLine1}\", tle2: \"${sat.tleLine2}\" })) } setTimeout(test1, 3000);"
                    }
                }

            }
        }

        section("py-5 text-center") {
            nav {
                ul("pagination") {

                    if(page!!-1 > 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                +"${page!!-1}"
                            }
                        }
                    } else
                    {
                        li("page-item disabled") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                    }

                    li("page-item active") {
                        a(classes = "page-link") {
                            href = "#"
                            +"${page!!}"
                            span("sr-only") { +"""(current)""" }
                        }
                    }
                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"${page!!+1}"
                        }
                    }

                    if(page!!-1 <= 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! + 2)
                                +"${page!!+2}"
                            }
                        }
                    }


                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"""Next"""
                        }
                    }
                }
            }
        }


    }
}*/


/*class SatelliteListContent : Template<FlowContent> {
    var page: Int? = null
    lateinit var sats: List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage: Long = 3*3;
        if (page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.find { Satellites.decayDate eq null }.limit(itemsXPage.toInt(), itemsXPage * page!! - itemsXPage).toList()
        }

        div("unity-desktop") {
            id = "unity-container"
            canvas {
                id = "unity-canvas"
                width = "960"
                height = "600"
            }
            div {
                id = "unity-loading-bar"
                div {
                    id = "unity-logo"
                }
                div {
                    id = "unity-progress-bar-empty"
                    div {
                        id = "unity-progress-bar-full"
                    }
                }
            }
        }
        script {
            src = "js/scriptBuildOrbits.js"
        }
        script {
            src = "js/scripts.js"
        }
        div("container px-5 card-columns") {
            for (sat in sats) {

                div("card") {
                    onClick = "window.location = '/view_satellite?id=${sat.noradCatId}';"

                    style = "width: 15rem;"
                    img(classes = "card-img-top") {
                        src =
                            "https://www.heavens-above.com/orbitdisplay.aspx?icon=iss&width=300&height=300&mode=A&satid=${sat.noradCatId}"
                        alt = "Card image cap"
                    }
                    div("card-block px-2") {
                        if(sat.objectName!!.length > 16)
                            h4("card-title pt-lg-2") { +"${sat.objectName!!.take(16)}..." }
                        else
                            h4("card-title pt-lg-2") { +"${sat.objectName}" }

                    }
                    div("card-footer w-100 text-muted") {
                        p("card-subtitle") { +"""Launch date: "${sat.launchDate}"""" }
                        p("card-subtitle") { +"""Norad ID: "${sat.noradCatId}"""" }
                        p("card-subtitle") { +"""Sat identifier: "${sat.objectID}"""" }
                    }
                }
                br { }

                script {
                    unsafe {
                        +"function test1() { u.SendMessage('GameManager', 'addSat', JSON.stringify({ tle1: \"${sat.tleLine1}\", tle2: \"${sat.tleLine2}\" })) } setTimeout(test1, 3000);"
                    }
                }

            }
        }

        section("py-5 text-center") {
            nav {
                ul("pagination") {

                    if(page!!-1 > 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                +"${page!!-1}"
                            }
                        }
                    } else
                    {
                        li("page-item disabled") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! - 1)
                                tabIndex = "-1"
                                +"""Previous"""
                            }
                        }
                    }

                    li("page-item active") {
                        a(classes = "page-link") {
                            href = "#"
                            +"${page!!}"
                            span("sr-only") { +"""(current)""" }
                        }
                    }
                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"${page!!+1}"
                        }
                    }

                    if(page!!-1 <= 0) {
                        li("page-item") {
                            a(classes = "page-link") {
                                href = "/satellites?page=" + (page!! + 2)
                                +"${page!!+2}"
                            }
                        }
                    }


                    li("page-item") {
                        a(classes = "page-link") {
                            href = "/satellites?page=" + (page!! + 1)
                            +"""Next"""
                        }
                    }
                }
            }
        }


    }
}*/

class ListItem : Template<FlowContent> {
    override fun FlowContent.apply() {
        TODO("Not yet implemented")
    }


}
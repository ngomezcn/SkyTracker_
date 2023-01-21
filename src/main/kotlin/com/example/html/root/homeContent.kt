package com.example.html.root

import com.example.models.SpaceTrack.STSatelliteCatalog
import kotlinx.html.FlowContent
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction

fun FlowContent.home() {
    lateinit var sat : STSatelliteCatalog

        header("bg-dark py-5") {
            div("container px-5") {
                div("row gx-5 align-items-center justify-content-center") {
                    div("col-lg-8 col-xl-7 col-xxl-6") {
                        div("my-5 text-center text-xl-start") {
                            h1("display-5 fw-bolder text-white mb-2") { +"""Spotting satellites has never been easier""" }
                            p("lead fw-normal text-white-50 mb-4") { +"""Quickly design and customize responsive mobile-first sites with Bootstrap, the world’s most popular front-end open source toolkit!""" }
                            div("d-grid gap-3 d-sm-flex justify-content-sm-center justify-content-xl-start") {
                                a(classes = "btn btn-primary btn-lg px-4 me-sm-3") {
                                    href = "/create_account"
                                    +"""Get Started"""
                                }
                                a(classes = "btn btn-outline-light btn-lg px-4") {
                                    href = "/sign_in"
                                    +"""Sign In"""
                                }
                            }
                        }
                    }
                    div("col-xl-5 col-xxl-6 d-none d-xl-block text-center") {
                        img(classes = "img-fluid rounded-3 my-5") {
                            //src = "https://wallup.net/wp-content/uploads/2015/12/220144-ALMA_Observatory-Chile-space-starry_night-Atacama_Desert-technology-galaxy-landscape.jpg"
                            src = "https://img.theculturetrip.com/1440x/smart/wp-content/uploads/2017/09/13986527890_364f4d138a_o.jpg"
                            alt = "..."
                        }
                    }
                }
            }
        }

/* CONSULTA SQL PARA MOSTRAR EL NUMERO DE SATS EN CADA ORBITA
SELECT CASE
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) <= 1  THEN 'HEO'
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) <= 11 THEN 'MEO'
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) > 11  THEN 'LEO'
        ELSE 'Others'
    END AS Orbit_category,
    count(id) AS total_num
FROM   satellites
GROUP  BY CASE
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) <= 1  THEN 'HEO'
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) <= 11 THEN 'MEO'
    WHEN (SUBSTRING("tleLine2", 53, 4)::double precision) > 11  THEN 'LEO'
        ELSE 'Others'
END;
*/

        var leoCount = 0
        var meoCount = 0
        var heoCount = 0

        transaction {
            exec("SELECT CASE " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) <= 1  THEN 'HEO' " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) <= 11 THEN 'MEO' " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) > 11  THEN 'LEO' " +
                    "        ELSE 'Others' " +
                    "    END AS Orbit_category, " +
                    "    count(id) AS total_count " +
                    "FROM satellites " +
                    "GROUP  BY CASE " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) <= 1  THEN 'HEO' " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) <= 11 THEN 'MEO' " +
                    "    WHEN (SUBSTRING(\"tleLine2\", 53, 4)::double precision) > 11  THEN 'LEO' " +
                    "        ELSE 'Others' " +
                    "END; ") {

                while (it.next()) {
                    when(it.getString("orbit_category")) {
                        "LEO" -> leoCount = it.getInt("total_count")
                        "MEO" -> meoCount = it.getInt("total_count")
                        "HEO" -> heoCount = it.getInt("total_count")
                    }
                }
            }
        }

        println(leoCount)
        println(meoCount)
        println(heoCount)

        // Features section-->"""
        section("py-5") {
            id = "features"
            div("container px-5 my-5") {
                div("row gx-5") {
                    div("col-lg-4 mb-5 mb-lg-0") {
                        h2("fw-bolder mb-0") { +"""Features that you will like""" }
                    }
                    div("col-lg-8") {
                        div("row gx-5 row-cols-1 row-cols-md-2") {
                            div("col mb-5 h-100") {
                                div("feature bg-primary bg-gradient text-white rounded-3 mb-3") {
                                    i("bi bi-envelope-fill") {
                                    }
                                }
                                h2("h5") { +"""Email notifications""" }
                                p("mb-0") { +"""Email notifications regarding your satellite watch list""" }
                            }
                            div("col mb-5 h-100") {
                                div("feature bg-primary bg-gradient text-white rounded-3 mb-3") {
                                    i("bi bi-cloud-arrow-up") {
                                    }
                                }
                                h2("h5") { +"""Upload your images""" }
                                p("mb-0") { +"""Image uploading system for all users to share their experiences""" }
                            }
                            div("col mb-5 mb-md-0 h-100") {
                                div("feature bg-primary bg-gradient text-white rounded-3 mb-3") {
                                    i("bi bi-chat") {
                                    }
                                }
                                h2("h5") { +"""Interactive orbit viewer""" }
                                p("mb-0") { +"""With the 3D visualizer the user can get an idea of all the satellites and their orbits.""" }
                            }
                            div("col h-100") {
                                div("feature bg-primary bg-gradient text-white rounded-3 mb-3") {
                                    i("bi bi-globe") {
                                    }
                                }
                                h2("h5") { +"""Chat""" }
                                p("mb-0") { +"""There is a section in each satellite where users can share their experiences.""" }
                            }
                        }
                    }
                }
            }
        }

}
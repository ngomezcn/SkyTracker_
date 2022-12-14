package com.example.orm

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


object ORM {
    lateinit var db : Database

    fun connect() {
        db = Database.connect("jdbc:postgresql://localhost:5432/postgres", driver = "com.impossibl.postgres.jdbc.PGDriver", user = "postgres", password = "1234")
       // db = Database.connect("jdbc:pgsql://localhost:5432/template1", driver = "com.impossibl.postgres.jdbc.PGDriver", user = "sjo", password = "p4ssword!")
    }

    fun createSchemas() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Satellites)
        }
    }

    fun loadInitialData() {
        println("LOADING DATA TO DATABASE")

        transaction {
            addLogger(StdOutSqlLogger)

            if(SatelliteDAO.all().count() < 100) {

                // Cargamos los sats desde un fichero o desde la api, por el momento lo dejo en el fichero porque son bastantes datos
                //satellitesList = Repository().getAllSatellites()
                var satellitesList = Json.decodeFromString<List<STSatelliteCatalog>>(File("sats.json").readText(Charsets.UTF_8))
                satellitesList = satellitesList!!.sortedBy { it.NORADCATID!!.toInt() }

                for (sat in satellitesList!!) {
                    SatelliteDAO.new {
                        noradCatId = sat.NORADCATID!!
                        objectName = sat.OBJECTNAME
                        objectID = sat.OBJECTID
                        countryCode = sat.COUNTRYCODE
                        launchDate = sat.LAUNCHDATE
                        decayDate = sat.DECAYDATE

                        tleLine0 = sat.TLELINE0
                        tleLine1 = sat.TLELINE1
                        tleLine2 = sat.TLELINE2
                    }
                }
            }
        }
    }
}

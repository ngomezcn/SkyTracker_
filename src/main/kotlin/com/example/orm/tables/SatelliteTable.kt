package com.example.orm.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object SatellitesTable : IntIdTable() { // https://www.space-track.org/basicspacedata/modeldef/class/gp/format/html
    val noradCatId: Column<String> = varchar("noradCatId",100)
    val objectName: Column<String?> = varchar("objectName", 100).nullable()
    val objectID: Column<String?> = varchar("objectID", 100).nullable()
    val countryCode: Column<String?> = varchar("countryCode", 100).nullable()
    val launchDate: Column<String?> = varchar("launchDate", 100).nullable()
    val site : Column<String?> = varchar("site", 100).nullable()
    val decayDate: Column<String?> = varchar("decayDate", 100).nullable()

    val tleLine0: Column<String?> = varchar("tleLine0", 100).nullable()
    val tleLine1: Column<String?> = varchar("tleLine1", 100).nullable()
    val tleLine2: Column<String?> = varchar("tleLine2", 100).nullable()
}

class SatelliteDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SatelliteDAO>(SatellitesTable) {
        fun getSatellite(id: Int) : SatelliteDAO?
        {
            var oSatellite: SatelliteDAO? = null
            transaction {
                val query = SatellitesTable.select {
                    SatellitesTable.id eq id
                }

                if (SatelliteDAO.wrapRows(query).count().toInt() > 0) {
                    oSatellite = SatelliteDAO.wrapRows(query).toList().first()
                }
            }

            return oSatellite
        }
    }

    var noradCatId by SatellitesTable.noradCatId
    var objectName by SatellitesTable.objectName
    var objectID by SatellitesTable.objectID
    var countryCode by SatellitesTable.countryCode
    var launchDate by SatellitesTable.launchDate
    var site by SatellitesTable.site
    var decayDate by SatellitesTable.decayDate

    var tleLine0 by SatellitesTable.tleLine0
    var tleLine1 by SatellitesTable.tleLine1
    var tleLine2 by SatellitesTable.tleLine2
}


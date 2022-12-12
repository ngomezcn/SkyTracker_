package com.example.orm.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Satellites : IntIdTable() { // https://www.space-track.org/basicspacedata/modeldef/class/gp/format/html
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
    companion object : IntEntityClass<SatelliteDAO>(Satellites)

    var noradCatId by Satellites.noradCatId
    var objectName by Satellites.objectName
    var objectID by Satellites.objectID
    var countryCode by Satellites.countryCode
    var launchDate by Satellites.launchDate
    var site by Satellites.site
    var decayDate by Satellites.decayDate

    var tleLine0 by Satellites.tleLine0
    var tleLine1 by Satellites.tleLine1
    var tleLine2 by Satellites.tleLine2
}

object User : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 50)
    val email: Column<String> = varchar("email", 50)
    val password: Column<String> = varchar("password", 50)
    val sessionCookie: Column<String> = varchar("sessionCookie", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_User_Id")
}
package com.example.orm.models

import com.example.orm.modelsoSatellite.UsersTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction

object UserTrackingSatTable : IntIdTable() {

    val idUser = reference("idUser", UsersTable)
    val idSatellite = reference("idSatellite", SatellitesTable)
    val trackingDate = datetime("trackingDate").defaultExpression(CurrentDateTime)
}

class UserTrackingSatDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserTrackingSatDAO>(UserTrackingSatTable){

        fun isSatTrackedByUser(userId: EntityID<Int>, satId: EntityID<Int>) : UserTrackingSatDAO?
        {

            var oUserTrackingSatDAO : UserTrackingSatDAO? = null

            transaction {
                addLogger(StdOutSqlLogger)

                val condition = Op.build { UserTrackingSatTable.idSatellite eq satId and (UserTrackingSatTable.idUser eq userId) }

                val query = SatellitesTable.select(condition) /*{
                    UserTrackingSatTable.idSatellite eq satId
                }*/

                   /* if (SatelliteDAO.wrapRows(query).count().toInt() > 0) {
                    oUserTrackingSatDAO = UserTrackingSatDAO.wrapRows(query).toList().first()
                }*/
            }

            return oUserTrackingSatDAO
        }

    }
    var idUser by UserTrackingSatTable.idUser
    var idSatellite by UserTrackingSatTable.idSatellite
    var trackingDate by UserTrackingSatTable.trackingDate
}

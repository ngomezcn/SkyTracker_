package com.example.orm.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object UsersTable : IntIdTable() {
    val username: Column<String> = varchar("username", 50)
    val firstName: Column<String> = varchar("firstName", 50)
    val surname: Column<String> = varchar("surname", 50)
    val email: Column<String> = varchar("email", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 50)
    val sessionCookie: Column<String> = varchar("sessionCookie", 50)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UsersTable)

    var username by UsersTable.username
    var firstName by UsersTable.firstName
    var surname by UsersTable.surname
    var email by UsersTable.email
    var password by UsersTable.password
    var sessionCookie by UsersTable.sessionCookie
}
package com.example.models.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlin.text.get

@Serializable
data class CreateAccountModel(val username: String, val firstName: String, val surname: String, val email: String, val password: String)

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)


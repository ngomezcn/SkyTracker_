package com.example.models.login

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountModel(val username: String, val firstName: String, val surname: String, val email: String, val password: String)

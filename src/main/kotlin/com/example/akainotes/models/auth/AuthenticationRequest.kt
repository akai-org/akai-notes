package com.example.akainotes.models.auth

import com.example.akainotes.models.User

data class AuthenticationRequest(val username: String, val password: String) {
    fun toUser(): User = User(email = username, pswd = password)
}
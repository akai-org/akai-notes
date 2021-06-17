package com.example.akainotes.models.auth

import com.example.akainotes.models.User
import org.springframework.security.crypto.password.PasswordEncoder

data class AuthenticationRequest(val username: String, val password: String) {
    fun toUser(passwordEncoder: PasswordEncoder): User = User(email = username, pswd = passwordEncoder.encode(password))
}

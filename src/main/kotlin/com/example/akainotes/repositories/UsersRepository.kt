package com.example.akainotes.repositories

import com.example.akainotes.models.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UsersRepository : CoroutineCrudRepository<User, String> {
    suspend fun findUserByEmail(email: String): User?
}

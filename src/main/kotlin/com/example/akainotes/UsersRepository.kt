package com.example.akainotes

import com.example.akainotes.models.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UsersRepository : CoroutineCrudRepository<User, String>
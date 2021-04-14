package com.example.akainotes.restcontrollers

import com.example.akainotes.models.User
import org.springframework.security.core.context.SecurityContextHolder

abstract class BaseAuthorizedRestController {
    fun getUser(): User {
        val user = SecurityContextHolder.getContext().authentication.principal as? User
        return user ?: throw Exception()
    }
}

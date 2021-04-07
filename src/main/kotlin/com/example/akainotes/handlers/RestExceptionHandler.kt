package com.example.akainotes.handlers

import com.example.akainotes.UserExistsException
import com.example.akainotes.models.errors.UserExistsError
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(UserExistsException::class)
    fun handleUserExistsException(
        userExistsException: UserExistsException
    ): ResponseEntity<UserExistsError> {
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(UserExistsError())
    }
}
package com.example.akainotes.handlers

import com.example.akainotes.exceptions.NoteNotFoundException
import com.example.akainotes.exceptions.UnauthorizedException
import com.example.akainotes.exceptions.UserExistsException
import com.example.akainotes.exceptions.WrongCredentialsException
import com.example.akainotes.models.errors.NoteNotFoundError
import com.example.akainotes.models.errors.UnauthorizedError
import com.example.akainotes.models.errors.UserExistsError
import com.example.akainotes.models.errors.WrongCredentialsError
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

    @ExceptionHandler(NoteNotFoundException::class)
    fun handleNoteNotFoundException(
        noteNotFoundException: NoteNotFoundException
    ): ResponseEntity<NoteNotFoundError> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(NoteNotFoundError())
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(
        unauthorizedException: UnauthorizedException
    ): ResponseEntity<UnauthorizedError> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(UnauthorizedError())
    }

    @ExceptionHandler(WrongCredentialsException::class)
    fun handleWrongCredentials(
        wrongCredentialsException: WrongCredentialsException
    ): ResponseEntity<WrongCredentialsError> {
        val wrongCredentialsError = WrongCredentialsError(wrongCredentialsException.message ?: "Wrong Credentials")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(wrongCredentialsError)
    }
}
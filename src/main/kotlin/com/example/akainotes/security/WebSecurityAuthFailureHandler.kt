package com.example.akainotes.security

import com.example.akainotes.models.errors.UnauthorizedError
import com.example.akainotes.models.errors.WrongCredentialsError
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.log

class WebSecurityAuthFailureHandler: AuthenticationFailureHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(WebSecurityAuthFailureHandler::class.java)
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        logger.error("AuthFailure exception: $exception")
        exception?.printStackTrace()
        when (exception) {
            is BadCredentialsException -> {
                response?.status = HttpStatus.BAD_REQUEST.value()
                val body = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(WrongCredentialsError())
                response?.outputStream?.println(body)
            }
            else -> {
                response?.status = HttpStatus.UNAUTHORIZED.value()
                val body = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(UnauthorizedError())
                response?.outputStream?.println(body)
            }
        }
    }
}

package com.example.akainotes.security

import com.example.akainotes.models.errors.UnauthorizedError
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebSecurityEntryPoint : AuthenticationEntryPoint {

    companion object {
        val logger = LoggerFactory.getLogger(WebSecurityEntryPoint::class.java)
    }


    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.error("Failure $authException")
        response?.status = HttpStatus.UNAUTHORIZED.value()
        val body = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(UnauthorizedError())
        response?.outputStream?.println(body)
    }
}

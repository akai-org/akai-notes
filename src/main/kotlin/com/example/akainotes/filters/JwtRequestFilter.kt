package com.example.akainotes.filters

import com.example.akainotes.NotesUserDetailsService
import com.example.akainotes.util.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val userDetailsService: NotesUserDetailsService,
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwt: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.replace("Bearer ","")
            println(jwt)
            username = jwtUtil.extractUsername(jwt)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtUtil.validateToken(jwt!!, userDetails)) {
                println("inside if")
                val usernamePasswordAuthToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthToken
            }
        }
        filterChain.doFilter(request, response)
    }
}
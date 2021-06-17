package com.example.akainotes.security

import com.example.akainotes.services.NotesUserDetailsService
import com.example.akainotes.util.JwtUtil
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebSecurityAuthSuccessHandler(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: NotesUserDetailsService
): SimpleUrlAuthenticationSuccessHandler() {

    val requestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val auth = SecurityContextHolder.getContext().authentication
        val savedRequest = requestCache.getRequest(request, response)

        val userName = auth?.name
        val userDetails = userDetailsService.loadUserByUsername(userName)
        val jwt = jwtUtil.generateToken(userDetails)
        response?.addHeader("Authorization", "Bearer $jwt")

        savedRequest?.let {
            val parameter = request?.getParameter(targetUrlParameter)
            val ok = isAlwaysUseDefaultTargetUrl || parameter != null && StringUtils.hasText(parameter)
            if (ok) {
                requestCache.removeRequest(request, response)
                clearAuthenticationAttributes(request)
                return
            }
        }
        clearAuthenticationAttributes(request)
    }

}

package com.example.akainotes.security

import com.example.akainotes.filters.JwtRequestFilter
import com.example.akainotes.services.NotesUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.vote.AuthenticatedVoter
import org.springframework.security.access.vote.RoleVoter
import org.springframework.security.access.vote.UnanimousBased
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.expression.WebExpressionVoter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: NotesUserDetailsService,
    private val successHandler: WebSecurityAuthSuccessHandler,
    private val jwtRequestFilter: JwtRequestFilter,
    private val unauthorizedHandler: AuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .authorizeRequests().antMatchers(HttpMethod.POST,"/register").permitAll()
            .anyRequest()
            .authenticated()
            .and().formLogin().loginProcessingUrl("/login")
            .usernameParameter("username").passwordParameter("password")
            .successHandler(successHandler)
            .failureHandler(WebSecurityAuthFailureHandler())
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun accessDecisionManager(): AccessDecisionManager {
        val decisionVoter = listOf(
            WebExpressionVoter(),
            AuthenticatedVoter()
        )
        return UnanimousBased(decisionVoter)
    }
}

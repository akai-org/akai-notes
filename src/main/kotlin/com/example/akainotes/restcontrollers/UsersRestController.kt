package com.example.akainotes.restcontrollers

import com.example.akainotes.NotesUserDetailsService
import com.example.akainotes.UsersRepository
import com.example.akainotes.models.User
import com.example.akainotes.models.auth.AuthenticationRequest
import com.example.akainotes.models.auth.AuthenticationResponse
import com.example.akainotes.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersRestController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: NotesUserDetailsService,
    private val jwtUtil: JwtUtil,
    private val usersRepository: UsersRepository
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*> {

        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
            )
        } catch (e: BadCredentialsException) {
            throw Exception("Incorrect username or password", e) // TODO own exception
        }

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtUtil.generateToken(userDetails)
        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }

    @Throws(UserExistsException::class)
    @PostMapping("/register")
    fun addUser(@RequestBody user: User) {
        //TODO add user
    }

}
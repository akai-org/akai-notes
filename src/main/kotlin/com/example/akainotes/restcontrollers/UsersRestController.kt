package com.example.akainotes.restcontrollers

import com.example.akainotes.services.NotesUserDetailsService
import com.example.akainotes.repositories.UsersRepository
import com.example.akainotes.exceptions.UserExistsException
import com.example.akainotes.exceptions.WrongCredentialsException
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

    @PostMapping("/login")
    suspend fun logIn(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
            )
        } catch (e: BadCredentialsException) {
            throw WrongCredentialsException("Incorrect username or password")
        }

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtUtil.generateToken(userDetails)
        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }

    @Throws(UserExistsException::class)
    @PostMapping("/register")
    suspend fun register(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<User> {
        usersRepository.findUserByEmail(authenticationRequest.username)?.let {
            throw UserExistsException()
        }
        checkCredentials(authenticationRequest.username, authenticationRequest.password)
        val newUser = authenticationRequest.toUser()
        usersRepository.save(newUser)
        return ResponseEntity.ok(newUser)
    }

    private fun checkCredentials(username: String, password: String) {
        if (username.length <= 8 || password.length <= 8) throw WrongCredentialsException("Email or/and password too short")

        val regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$".toRegex(setOf(RegexOption.IGNORE_CASE))
        if (!regex.matches(username)) throw WrongCredentialsException("Wrong email")
    }

}

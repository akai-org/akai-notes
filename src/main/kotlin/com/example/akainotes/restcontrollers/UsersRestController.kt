package com.example.akainotes.restcontrollers

import com.example.akainotes.NotesUserDetailsService
import com.example.akainotes.UserExistsException
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
    suspend fun logIn(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
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
    suspend fun addUser(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<User> {
        usersRepository.findUserByEmail(authenticationRequest.username)?.let {
            throw UserExistsException()
        }
        areCredentialsCorrect(authenticationRequest.username, authenticationRequest.password)
        val newUser = authenticationRequest.toUser()
        usersRepository.save(newUser)
        return ResponseEntity.ok(newUser)
    }

    private fun AuthenticationRequest.toUser(): User = User(email = username, pswd = password)

    private fun areCredentialsCorrect(username: String, password: String): Boolean {
        if (username.length <= 8 || password.length <= 8) return false

        val regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$".toRegex()
        if (!regex.matches(username)) return false
        //TODO throw exception
        return true
    }

}
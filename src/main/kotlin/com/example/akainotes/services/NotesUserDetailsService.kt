package com.example.akainotes.services

import com.example.akainotes.repositories.UsersRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class NotesUserDetailsService(private val usersRepository: UsersRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails = runBlocking {
        if (username == null) throw UsernameNotFoundException("Empty email")

        return@runBlocking usersRepository.findUserByEmail(username)
            ?: throw UsernameNotFoundException("$username not found")
    }

}

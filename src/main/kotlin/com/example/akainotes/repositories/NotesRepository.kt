package com.example.akainotes.repositories

import com.example.akainotes.models.Note
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface NotesRepository : CoroutineCrudRepository<Note, String> {

    @Query("{'_id' : ?0 , 'userId' : ?1}")
    suspend fun findByIdAndUserId(id: String, userId: String): Note?

    @Query("{'userId' : ?0 , '\$or' : [ {'title' : /?1/}, {'note' : /?1/}]}")
    suspend fun findByUserIdAndSearchQuery(userId: String, searchQuery: String): List<Note>
}

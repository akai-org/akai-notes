package com.example.akainotes.repositories

import com.example.akainotes.models.Note
import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface NotesRepository : CoroutineCrudRepository<Note, String> {
    suspend fun findNotesByUserId(userId: String) : List<Note>

    @Query("{'_id' : ?0 , 'userId' : ?1}")
    suspend fun findByIdAndUserId(id: String, userId: String) : Note?
}

package com.example.akainotes

import com.example.akainotes.models.Note
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface NotesRepository : CoroutineCrudRepository<Note, String> {
    fun findNoteByUserId(userId: String) : Note
    fun findNotesByUserId(userId: String) : Flow<Note>
}
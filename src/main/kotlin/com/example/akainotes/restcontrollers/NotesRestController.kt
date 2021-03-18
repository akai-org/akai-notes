package com.example.akainotes.restcontrollers

import com.example.akainotes.NotesRepository
import com.example.akainotes.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notes")
class NotesRestController(private val repository: NotesRepository) {

    val notes = arrayListOf<Note>()

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNotes(): ResponseEntity<Flow<Note>> {
        // TODO get user id
        val userId = "wojtek"

        return ResponseEntity.ok(repository.findNotesByUserId(userId))
    }

    @GetMapping("/{noteId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNote(@PathVariable("noteId") noteId: String): ResponseEntity<Note> {
        // TODO get user id

//        return repository.findById(noteId)?.let { ResponseEntity.ok(it) } ?: ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.of(Optional.ofNullable(repository.findById(noteId)))
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun postNote(@RequestBody note: Note) {
        // TODO get user id
        val userId = "wojtek"

        repository.save(note.apply { this.userId = userId })
    }

    @PutMapping("/{noteId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateNote(@PathVariable("noteId") noteId: String, @RequestBody note: Note) {
        // TODO get user id
        val noteToRemove = notes.find { it.id == noteId }
        notes.remove(noteToRemove)
        notes.add(note)
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable("noteId") noteId: String) {
        // TODO get user id
        val noteToRemove = notes.find { it.id == noteId }
        notes.remove(noteToRemove)
    }
}
package com.example.akainotes.restcontrollers

import com.example.akainotes.NotesRepository
import com.example.akainotes.models.Note
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notes")
class NotesRestController(private val repository: NotesRepository) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNotes(): ResponseEntity<List<Note>> {
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
    suspend fun updateNote(@PathVariable("noteId") noteId: String, @RequestBody note: Note) {
        // TODO get user id
        val userId = "wojtek"

        note.id = noteId
        note.userId = userId
        repository.save(note)
    }

    @DeleteMapping("/{noteId}")
    suspend fun deleteNote(@PathVariable("noteId") noteId: String) {
        // TODO get user id
        repository.deleteById(noteId)
    }
}
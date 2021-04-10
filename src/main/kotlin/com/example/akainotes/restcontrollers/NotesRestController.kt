package com.example.akainotes.restcontrollers

import com.example.akainotes.NotesRepository
import com.example.akainotes.exceptions.NoteNotFoundException
import com.example.akainotes.models.Note
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
class NotesRestController(private val repository: NotesRepository) : BaseAuthorizedRestController() {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNotes(): ResponseEntity<List<Note>> {
        val userId = getUser().id

        return ResponseEntity.ok(repository.findNotesByUserId(userId))
    }

    @Throws(NoteNotFoundException::class)
    @GetMapping("/{noteId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNote(@PathVariable("noteId") noteId: String): ResponseEntity<Note> {
        val userId = getUser().id
        val note = repository.findById(noteId)?.takeIf { it.userId == userId }

        return note?.let { ResponseEntity.ok(it) } ?: throw NoteNotFoundException()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun postNote(@RequestBody note: Note) {
        val userId = getUser().id

        repository.save(note.apply { this.userId = userId })
    }

    @Throws(NoteNotFoundException::class)
    @PutMapping("/{noteId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun updateNote(@PathVariable("noteId") noteId: String, @RequestBody newNote: Note) {
        val userId = getUser().id
        val oldNote = repository.findById(noteId)

        if (oldNote == null || oldNote.userId != userId) {
            throw NoteNotFoundException()
        }

        newNote.id = noteId
        newNote.userId = userId
        repository.save(newNote)
    }

    @Throws(NoteNotFoundException::class)
    @DeleteMapping("/{noteId}")
    suspend fun deleteNote(@PathVariable("noteId") noteId: String) {
        val userId = getUser().id
        val oldNote = repository.findById(noteId)

        if (oldNote == null || oldNote.userId != userId) {
            throw NoteNotFoundException()
        }

        repository.deleteById(noteId)
    }
}
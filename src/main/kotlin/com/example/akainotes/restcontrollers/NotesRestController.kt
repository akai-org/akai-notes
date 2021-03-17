package com.example.akainotes.restcontrollers

import com.example.akainotes.models.Note
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
class NotesRestController {

    val notes = arrayListOf<Note>()

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNotes(): ResponseEntity<List<Note>> {
        // TODO get user id
        return ResponseEntity.ok(notes.filter { it.userId == 1L })
    }

    @GetMapping("/{noteId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNote(@PathVariable("noteId") noteId: Long): ResponseEntity<Note> {
        // TODO get user id
        return ResponseEntity.ok(notes.first { it.id == noteId })
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun postNote(@RequestBody note: Note) {
        // TODO get user id
        notes.add(note)
    }

    @PutMapping("/{noteId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateNote(@PathVariable("noteId") noteId: Long, @RequestBody note: Note) {
        // TODO get user id
        val noteToRemove = notes.find { it.id == noteId }
        notes.remove(noteToRemove)
        notes.add(note)
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable("noteId") noteId: Long) {
        // TODO get user id
        val noteToRemove = notes.find { it.id == noteId }
        notes.remove(noteToRemove)
    }
}
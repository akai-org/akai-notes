package com.example.akainotes.models.errors

class NoteNotFoundError : ApiError() {
    override val message: String
        get() = "Note could not be found"
    override val code: String
        get() = "note_not_found"
}
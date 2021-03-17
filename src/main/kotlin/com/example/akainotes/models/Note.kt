package com.example.akainotes.models

data class Note(
    val id: Long,
    val userId: Long,
    val title: String,
    val note: String,
)

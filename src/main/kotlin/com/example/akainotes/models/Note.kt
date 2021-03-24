package com.example.akainotes.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Note(
    @Id
    var id: String = "",
    @JsonIgnore
    var userId: String = "",
    val title: String,
    val note: String,
)

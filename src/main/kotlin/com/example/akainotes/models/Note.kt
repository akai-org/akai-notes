package com.example.akainotes.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Note(
    @JsonProperty(access = Access.READ_ONLY)
    @Id
    val id: String = "",
    @JsonIgnore
    var userId: String = "",
    val title: String,
    val note: String,
)

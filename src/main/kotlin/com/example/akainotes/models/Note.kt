package com.example.akainotes.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Note(
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var id: String = "",
    @JsonIgnore
    var userId: String = "",
    val title: String,
    val note: String,
)

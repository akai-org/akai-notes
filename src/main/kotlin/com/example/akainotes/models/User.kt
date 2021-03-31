package com.example.akainotes.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    var id: String = "",
    val email: String,
    val password: String,
)

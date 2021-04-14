package com.example.akainotes.models.errors

abstract class ApiError {
    abstract val message: String
    abstract val code: String
}


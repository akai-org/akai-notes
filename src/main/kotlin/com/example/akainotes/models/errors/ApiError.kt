package com.example.akainotes.models.errors

abstract class ApiError {
    abstract val message: String
    abstract val code: String
}

class UserExistsError : ApiError() {
    override val message: String
        get() = "User already exists"
    override val code: String
        get() = "already_exists"
}
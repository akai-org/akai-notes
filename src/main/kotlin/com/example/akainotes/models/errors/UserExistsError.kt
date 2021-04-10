package com.example.akainotes.models.errors

class UserExistsError : ApiError() {
    override val message: String
        get() = "User already exists"
    override val code: String
        get() = "already_exists"
}
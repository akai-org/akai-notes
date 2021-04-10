package com.example.akainotes.models.errors

class UnauthorizedError : ApiError() {
    override val message: String
        get() = "Unauthorized user"
    override val code: String
        get() = "unauthorized"
}
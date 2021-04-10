package com.example.akainotes.models.errors

class WrongCredentialsError(override val message: String = "Wrong credentials") : ApiError() {
    override val code: String
        get() = "wrong_credentials"
}
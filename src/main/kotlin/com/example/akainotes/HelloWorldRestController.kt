package com.example.akainotes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldRestController {

    @GetMapping("/")
    fun helloWorld(): String {
        return "Hello World!"
    }
}
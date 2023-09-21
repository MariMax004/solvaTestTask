package com.example.solvatask.error.dto

class InvalidDataException private constructor(message: String) : RuntimeException(message) {
    companion object {
        fun throwWithMessage(message: String): Nothing {
            throw InvalidDataException(message)
        }
    }
}
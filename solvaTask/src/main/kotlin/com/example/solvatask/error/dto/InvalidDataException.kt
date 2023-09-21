package com.example.solvatask.error.dto

import java.time.LocalDateTime

data class InvalidDataException(
        val error: String,
        val time: LocalDateTime = LocalDateTime.now()
) : RuntimeException() {
    companion object {
        fun throwWithMessage(message: String): InvalidDataException {
            return InvalidDataException(message)
        }
    }
}
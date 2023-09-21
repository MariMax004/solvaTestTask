package com.example.solvatask.error.dto

import java.time.LocalDateTime

data class ErrorResponse(
        val error: String?,
        val time: LocalDateTime = LocalDateTime.now()
)

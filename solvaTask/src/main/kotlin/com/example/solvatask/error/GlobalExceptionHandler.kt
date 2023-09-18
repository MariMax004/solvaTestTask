package com.example.solvatask.error;

import com.example.solvatask.error.dto.ErrorResponse
import com.example.solvatask.error.dto.InvalidData
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidData::class)
    fun handleInvalidData(ex: InvalidData): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Wrong request data or any field is empty")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
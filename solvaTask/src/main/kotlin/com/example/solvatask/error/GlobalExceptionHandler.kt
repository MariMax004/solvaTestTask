package com.example.solvatask.error;

import com.example.solvatask.error.dto.ErrorResponse
import com.example.solvatask.error.dto.InvalidDataException
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityNotFoundException


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidDataException::class)
    fun handleInvalidData(ex: InvalidDataException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
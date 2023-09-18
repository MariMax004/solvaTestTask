package com.example.solvatask.controller

import com.example.solvatask.request.CreateTransactionRequestDto
import com.example.solvatask.response.CreateTransactionResponseDto
import com.example.solvatask.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(val transactionService: TransactionService) {

    @PostMapping("/")
    fun createTransaction(@RequestBody transactionRequest: CreateTransactionRequestDto)
            : ResponseEntity<CreateTransactionResponseDto> {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest))
    }

    @GetMapping("/exceeded/{bankAccount}")
    fun getTransactionsExceed(@PathVariable bankAccount: String)
            : ResponseEntity<List<CreateTransactionResponseDto>>? {
        return ResponseEntity.ok(transactionService.getTransactionsExceed(bankAccount))
    }
}
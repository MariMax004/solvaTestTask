package com.example.solvatask.controller

import com.example.solvatask.config.integration.CurrencyFeignClient
import com.example.solvatask.dto.CreateTransactionRequestDto
import com.example.solvatask.dto.CreateTransactionResponseDto
import com.example.solvatask.error.dto.InvalidDataException
import com.example.solvatask.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(val transactionService: TransactionService,
                            val currencyFeignClient: CurrencyFeignClient) {

    @PostMapping("/")
    fun createTransaction(@RequestBody transactionRequest: CreateTransactionRequestDto)
            : ResponseEntity<CreateTransactionResponseDto> {
        require(!transactionRequest.accountFrom.isNullOrBlank()) {
            throw InvalidDataException.throwWithMessage("BankAccount mustn't be null or empty")
        }
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest))
    }

    @GetMapping("/exceeded/{bankAccount}")
    fun getTransactionsExceed(@PathVariable bankAccount: String)
            : ResponseEntity<List<CreateTransactionResponseDto>>? {
        return ResponseEntity.ok(transactionService.getTransactionsExceed(bankAccount))
    }

    @GetMapping("/exceeded/usd/{bankAccount}")
    fun getTransactionsAsync(@PathVariable bankAccount: String)
            : ResponseEntity<List<CreateTransactionResponseDto>> {
        return ResponseEntity.ok(transactionService.getTransactionsExceedInUSD(bankAccount))
    }
}

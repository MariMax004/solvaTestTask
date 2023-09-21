package com.example.solvatask.dto

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CreateTransactionResponseDto(
        var accountFrom: String,
        val accountTo: String,
        val datetime: LocalDateTime,
        val limitExceed: Boolean,
        var sum: BigDecimal,
        var currencyShortcode: CurrencyShortcode,
        val expenseCategory: ExpenseCategory,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var limit: CreateLimitResponseDto? = null,
)
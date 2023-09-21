package com.example.solvatask.dto

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class CreateTransactionRequestDto(
        @NotNull
        val accountFrom: String,
        val accountTo: String,
        @NotNull
        val sum: BigDecimal,
        @NotNull
        val currencyShortcode: CurrencyShortcode,
        @NotNull
        val expenseCategory: ExpenseCategory
)
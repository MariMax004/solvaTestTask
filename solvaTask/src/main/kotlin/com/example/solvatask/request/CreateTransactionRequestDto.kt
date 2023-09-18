package com.example.solvatask.request

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import java.math.BigDecimal

class CreateTransactionRequestDto(
        val accountFrom: String? = null,
        val accountTo: String? = null,
        val sum: BigDecimal? = null,
        val currencyShortcode: CurrencyShortcode? = null,
        val expenseCategory: ExpenseCategory? = null
)
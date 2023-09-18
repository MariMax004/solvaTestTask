package com.example.solvatask.response

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CreateTransactionResponseDto(
        var accountFrom: String? = null,
        val accountTo: String? = null,
        val datetime: LocalDateTime? = null,
        val limitExceed: Boolean? = null,
        val sum: BigDecimal? = null,
        val currencyShortcode: CurrencyShortcode? = null,
        val expenseCategory: ExpenseCategory? = null
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var limit: CreateLimitResponseDto? = null
}

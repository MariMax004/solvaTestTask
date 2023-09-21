package com.example.solvatask.entity

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class TransactionLimitEntity(
        @Id
        val id: Long,
        val accountFrom: String,
        val accountTo: String,
        val datetime: LocalDateTime,
        val limitExceed: Boolean,
        val sum: BigDecimal,
        @Enumerated(EnumType.STRING)
        val currencyShortcode: CurrencyShortcode,
        @Enumerated(EnumType.STRING)
        val expenseCategory: ExpenseCategory,
        val limitSum: BigDecimal,
        val limitDatetime: LocalDateTime,
        @Enumerated(EnumType.STRING)
        val limitCurrencyShortcode: CurrencyShortcode,
)
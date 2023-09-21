package com.example.solvatask.entity

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "transaction")
data class TransactionEntity(
        @Column(name = "account_from")
        val accountFrom: String,

        @Column(name = "account_to")
        val accountTo: String,

        @Column(name = "sum")
        val sum: BigDecimal,

        @Column(name = "currency_shortcode")
        @Enumerated(EnumType.STRING)
        val currencyShortcode: CurrencyShortcode,

        @Column(name = "expense_category")
        @Enumerated(EnumType.STRING)
        val expenseCategory: ExpenseCategory,

        val datetime: LocalDateTime,

        @Column(name = "limit_exceed")
        val limitExceed: Boolean,

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
        @SequenceGenerator(name = "transaction_seq", allocationSize = 1, sequenceName = "transaction_seq")
        val id: Long? = null
)
package com.example.solvatask.entity

import com.example.solvatask.enums.CurrencyShortcode
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "client_limit")
data class LimitEntity(
        @Column(name = "limit_sum")
        val limitSum: BigDecimal,

        @Column(name = "balance_product")
        var balanceProduct: BigDecimal,

        @Column(name = "balance_service")
        var balanceService: BigDecimal,

        @Column(name = "bank_account")
        val bankAccount: String,

        val datetime: LocalDateTime,

        @Column(name = "currency_shortcode")
        @Enumerated(EnumType.STRING)
        val currencyShortcode: CurrencyShortcode,

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "limit_seq")
        @SequenceGenerator(name = "limit_seq", allocationSize = 1, sequenceName = "limit_seq")
        val id: Long? = null,
)

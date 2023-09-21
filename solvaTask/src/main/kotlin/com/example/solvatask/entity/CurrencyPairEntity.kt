package com.example.solvatask.entity

import com.example.solvatask.enums.CurrencyShortcode
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "currency_pair")
data class CurrencyPairEntity(
        @Column(name = "shortcode_from")
        @Enumerated(EnumType.STRING)
        val shortcodeFrom: CurrencyShortcode,

        @Column(name = "shortcode_to")
        @Enumerated(EnumType.STRING)
        val shortcodeTo: CurrencyShortcode,

        @Column(name = "date")
        val date: LocalDate,

        @Column(name = "close")
        val close: BigDecimal,
        @Id
        var id: String? = null
)

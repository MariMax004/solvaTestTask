package com.example.solvatask.dto

import com.example.solvatask.enums.CurrencyShortcode
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CreateLimitResponseDto(
        var limitSum: BigDecimal,
        var datetime: LocalDateTime,
        var currencyShortcode: CurrencyShortcode,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val bankAccount: String
)

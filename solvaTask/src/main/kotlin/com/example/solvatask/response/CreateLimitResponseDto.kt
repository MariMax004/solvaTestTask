package com.example.solvatask.response

import com.example.solvatask.enums.CurrencyShortcode
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CreateLimitResponseDto(
        var limitSum: BigDecimal? = null,

        var datetime: LocalDateTime? = null,

        var currencyShortcode: CurrencyShortcode? = null,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val bankAccount: String? = null
)
package com.example.solvatask.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal


data class CreateLimitRequestDto(
        var limitSum: BigDecimal = BigDecimal(1000),
        @JsonProperty(required = true)
        var bankAccount: String
)

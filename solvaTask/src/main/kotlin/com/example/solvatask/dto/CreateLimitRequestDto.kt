package com.example.solvatask.dto

import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


data class CreateLimitRequestDto(
        var limitSum: BigDecimal = BigDecimal(1000),
        @NotNull
        @NotEmpty
        var bankAccount: String
)

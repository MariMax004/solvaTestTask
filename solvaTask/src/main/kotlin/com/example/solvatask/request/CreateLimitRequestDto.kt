package com.example.solvatask.request

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

class CreateLimitRequestDto {
    var limitSum: BigDecimal? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var bankAccount: String? = null
}
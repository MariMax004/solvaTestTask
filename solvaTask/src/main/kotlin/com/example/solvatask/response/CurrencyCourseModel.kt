package com.example.solvatask.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class CurrencyCourseModel(
        val meta: Meta,
        val values: List<Values>
) {
    data class Meta(val symbol: String)

    data class Values(
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            val datetime: LocalDateTime,
            val close: BigDecimal
    )
}

package com.example.solvatask.response

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate

data class CurrencyCourseModel(
        @JsonAlias("USD/KZT:Huobi", "USD/RUB:Huobi", "USD/AED:Huobi")
        val pair: DataCurrency
) {
    data class DataCurrency(
            val meta: Meta,
            val values: List<Values>
    )

    data class Meta(val symbol: String)

    data class Values(
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            val datetime: LocalDate,
            val close: BigDecimal
    )
}

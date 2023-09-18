package com.example.solvatask.resttemplatemodel

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate

class Values {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val datetime: LocalDate? = null
    val close: BigDecimal? = null
}
package com.example.solvatask.mapper

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.model.LimitEntity
import com.example.solvatask.response.CreateLimitResponseDto
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Component
class LimitMapper {
    fun convertToCreateLimitResponseDto(limit: LimitEntity?): CreateLimitResponseDto {
        return CreateLimitResponseDto(
                datetime = limit?.datetime,
                limitSum = limit?.limitSum,
                currencyShortcode = limit?.currencyShortcode,
                bankAccount = limit?.bankAccount
        )
    }
    fun convertToLimit(bankAccount: String?, limitSum: BigDecimal?): LimitEntity {
        val limit = LimitEntity()
        limit.bankAccount = bankAccount
        limit.limitSum = limitSum
        limit.datetime = LocalDateTime.now()
        limit.currencyShortcode = CurrencyShortcode.USD
        limit.balanceProduct = limitSum
        limit.balanceService = limitSum
        return limit;
    }

}
package com.example.solvatask.mapper

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.dto.CreateLimitResponseDto
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

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

    fun convertToLimit(bankAccount: String, limitSum: BigDecimal): LimitEntity {
        return LimitEntity(
                limitSum = limitSum,
                balanceProduct = limitSum,
                balanceService = limitSum,
                bankAccount = bankAccount,
                datetime = LocalDateTime.now(),
                currencyShortcode = CurrencyShortcode.USD)
    }
}
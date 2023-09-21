package com.example.solvatask.mapper

import com.example.solvatask.entity.TransactionEntity
import com.example.solvatask.entity.TransactionLimitEntity
import com.example.solvatask.dto.CreateTransactionRequestDto
import com.example.solvatask.dto.CreateLimitResponseDto
import com.example.solvatask.dto.CreateTransactionResponseDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TransactionMapper {
    fun convertToCreateTransactionResponseDto(transaction: TransactionEntity): CreateTransactionResponseDto {
        return CreateTransactionResponseDto(
                accountFrom = transaction.accountFrom,
                accountTo = transaction.accountTo,
                sum = transaction.sum,
                currencyShortcode = transaction.currencyShortcode,
                expenseCategory = transaction.expenseCategory,
                limitExceed = transaction.limitExceed,
                datetime = transaction.datetime
        )
    }

    fun convertToTransaction(transactionRequest: CreateTransactionRequestDto, isExceed: Boolean): TransactionEntity {
        return TransactionEntity(
                accountFrom = transactionRequest.accountFrom,
                accountTo = transactionRequest.accountTo,
                sum = transactionRequest.sum,
                currencyShortcode = transactionRequest.currencyShortcode,
                expenseCategory = transactionRequest.expenseCategory,
                datetime = LocalDateTime.now(),
                limitExceed = isExceed
        )
    }

    fun convertToCreateTransactionResponseDto(transactionLimit: TransactionLimitEntity): CreateTransactionResponseDto {
        return CreateTransactionResponseDto(
                accountFrom = transactionLimit.accountFrom,
                accountTo = transactionLimit.accountTo,
                datetime = transactionLimit.datetime,
                limitExceed = transactionLimit.limitExceed,
                sum = transactionLimit.sum,
                currencyShortcode = transactionLimit.currencyShortcode,
                expenseCategory = transactionLimit.expenseCategory,
                limit = CreateLimitResponseDto(
                        limitSum = transactionLimit.limitSum,
                        datetime = transactionLimit.limitDatetime,
                        currencyShortcode = transactionLimit.limitCurrencyShortcode
                )
        )
    }
}

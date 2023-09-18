package com.example.solvatask.mapper

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.example.solvatask.model.TransactionEntity
import com.example.solvatask.request.CreateTransactionRequestDto
import com.example.solvatask.response.CreateLimitResponseDto
import com.example.solvatask.response.CreateTransactionResponseDto
import jakarta.persistence.Tuple
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

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
        val transaction = TransactionEntity()
        transaction.accountTo = transactionRequest.accountTo
        transaction.accountFrom = transactionRequest.accountFrom
        transaction.datetime = Date()
        transaction.sum = transactionRequest.sum
        transaction.currencyShortcode = transactionRequest.currencyShortcode
        transaction.expenseCategory = transactionRequest.expenseCategory
        transaction.limitExceed = isExceed
        return transaction
    }

    fun convertToCreateTransactionResponseDto(tuple: Tuple): CreateTransactionResponseDto {
        val transaction = CreateTransactionResponseDto(
                tuple.get(1, String::class.java),
                tuple.get(2, String::class.java),
                tuple.get(3, Date::class.java),
                tuple.get(4) as Boolean,
                tuple.get(5, BigDecimal::class.java),
                CurrencyShortcode.valueOf(tuple.get(6, String::class.java) as String),
                ExpenseCategory.valueOf(tuple.get(7, String::class.java) as String)
        )
        val limit = CreateLimitResponseDto()
        limit.limitSum = tuple.get(8, BigDecimal::class.java)
        limit.datetime = tuple.get(9, Date::class.java)
        limit.currencyShortcode = CurrencyShortcode.valueOf(tuple.get(10, String::class.java) as String)
        transaction.limit = limit
        return transaction
    }
}

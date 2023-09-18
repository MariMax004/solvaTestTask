package com.example.solvatask.service

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.example.solvatask.mapper.TransactionMapper
import com.example.solvatask.model.LimitEntity
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.repository.TransactionRepository
import com.example.solvatask.request.CreateTransactionRequestDto
import com.example.solvatask.response.CreateTransactionResponseDto
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class TransactionService(val transactionMapper: TransactionMapper,
                         val transactionRepository: TransactionRepository,
                         val limitRepository: LimitRepository,
                         val limitService: LimitService,
                         val currencyPairRepository: CurrencyPairRepository) {

    private val logger = KotlinLogging.logger {}

    fun getTransactionsExceed(bankAccount: String): List<CreateTransactionResponseDto> {
        val tuples = transactionRepository.findTransactionsWithLimitExceed(bankAccount)
        return tuples.stream().map(transactionMapper::convertToCreateTransactionResponseDto).toList()
    }

    @Transactional
    fun createTransaction(transactionRequest: CreateTransactionRequestDto): CreateTransactionResponseDto {
        val sum = transactionRequest.sum
        val accountFrom = transactionRequest.accountFrom
        val expenseCategory = transactionRequest.expenseCategory
        var limit = limitRepository.getLastLimit(accountFrom)
                .orElseGet { limitService.createClientLimit(accountFrom, BigDecimal(1000)) }

        when (expenseCategory) {
            ExpenseCategory.SERVICE -> limit.balanceService = getBalanceAfterTransaction(
                    limit.balanceService,
                    sum,
                    transactionRequest.currencyShortcode)

            ExpenseCategory.PRODUCT -> limit.balanceProduct = getBalanceAfterTransaction(
                    limit.balanceProduct,
                    sum,
                    transactionRequest.currencyShortcode)

            else -> {
                print("wrong expense category")
            }
        }
        val isExceed = isTransactionExceed(expenseCategory, limit)
        var transaction = transactionMapper.convertToTransaction(transactionRequest, isExceed)
        limitRepository.save(limit)
        transactionRepository.save(transaction)

        return transactionMapper.convertToCreateTransactionResponseDto(transaction)
    }

    fun getBalanceAfterTransaction(
            limitBalance: BigDecimal?,
            transactionSum: BigDecimal?,
            shortcode: CurrencyShortcode?): BigDecimal? {
        var valueUsd = transactionSum
        if (shortcode != CurrencyShortcode.USD) {
            val currencyValue = currencyPairRepository
                    .getLastCurrencyCoursePair(CurrencyShortcode.USD.toString(), shortcode.toString())
                    ?.close

            if (currencyValue != null && currencyValue.compareTo(BigDecimal.ZERO) != 0) {
                valueUsd = transactionSum?.divide(currencyValue, 3, RoundingMode.HALF_UP)
            }
        }
        return limitBalance?.subtract(valueUsd)
    }


    fun isTransactionExceed(expenseCategory: ExpenseCategory?, limit: LimitEntity): Boolean {
        return when (expenseCategory) {
            ExpenseCategory.SERVICE -> BigDecimal.ZERO.compareTo(limit.balanceService) > 0
            ExpenseCategory.PRODUCT -> BigDecimal.ZERO.compareTo(limit.balanceProduct) > 0
            else -> {
                false
            }
        }
    }
}
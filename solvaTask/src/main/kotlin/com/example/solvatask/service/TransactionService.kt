package com.example.solvatask.service

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.example.solvatask.mapper.TransactionMapper
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.entity.TransactionLimitEntity
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.repository.TransactionLimitRepository
import com.example.solvatask.repository.TransactionRepository
import com.example.solvatask.dto.CreateTransactionRequestDto
import com.example.solvatask.dto.CreateTransactionResponseDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.math.RoundingMode
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.streams.toList

@Service
class TransactionService(val transactionMapper: TransactionMapper,
                         val transactionRepository: TransactionRepository,
                         val transactionLimitRepository: TransactionLimitRepository,
                         val limitRepository: LimitRepository,
                         val limitService: LimitService,
                         val currencyPairRepository: CurrencyPairRepository) {

    val executorService: ExecutorService = Executors
            .newFixedThreadPool(enumValues<CurrencyShortcode>().size)

    fun getTransactionsExceed(bankAccount: String): List<CreateTransactionResponseDto> {
        val tuples: List<TransactionLimitEntity> = transactionLimitRepository.findTransactionsLimits(bankAccount)
        return tuples
                .stream()
                .map(transactionMapper::convertToCreateTransactionResponseDto).toList()
    }

    fun getTransactionsExceedInUSD(bankAccount: String): CompletableFuture<List<CreateTransactionResponseDto>> {
        val transactions = transactionLimitRepository.findTransactionsLimits(bankAccount)
        val groupedTransactions = transactions.groupBy { it.currencyShortcode }
        val futures = groupedTransactions.map { (shortcode, transactions) ->
            CompletableFuture.supplyAsync({
                val transformedTransactions: List<CreateTransactionResponseDto>
                if (shortcode != CurrencyShortcode.USD) {
                    transformedTransactions = transactions.map { dto ->
                        val transactionResponseDto = transactionMapper.convertToCreateTransactionResponseDto(dto)
                        transactionResponseDto.sum = getSumInUSD(dto.sum, dto.currencyShortcode, dto.datetime.toLocalDate())
                        transactionResponseDto.currencyShortcode = CurrencyShortcode.USD
                        transactionResponseDto
                    }
                    transformedTransactions
                } else {
                    transformedTransactions = transactions.map { dto ->
                        transactionMapper.convertToCreateTransactionResponseDto(dto)
                    }
                    transformedTransactions
                }
            }, executorService)
        }

        return CompletableFuture.allOf(*futures.toTypedArray())
                .thenApply {
                    futures.flatMap { it.join() }
                }
    }

    @Transactional
    fun createTransaction(transactionRequest: CreateTransactionRequestDto): CreateTransactionResponseDto {
        val sum = transactionRequest.sum
        val accountFrom = transactionRequest.accountFrom
        val expenseCategory = transactionRequest.expenseCategory
        val limit = limitRepository.getLastLimit(accountFrom)
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
        }

        val isExceed = isTransactionExceed(expenseCategory, limit)
        val transaction = transactionMapper.convertToTransaction(transactionRequest, isExceed)
        limitRepository.save(limit)
        transactionRepository.save(transaction)

        return transactionMapper.convertToCreateTransactionResponseDto(transaction)
    }

    fun getBalanceAfterTransaction(
            limitBalance: BigDecimal,
            transactionSum: BigDecimal,
            shortcode: CurrencyShortcode): BigDecimal {
        return if (shortcode != CurrencyShortcode.USD) {
            val valueUsd = getSumInUSD(transactionSum, shortcode, LocalDate.now())
            limitBalance.subtract(valueUsd)
        } else {
            limitBalance.subtract(transactionSum)
        }
    }

    fun getSumInUSD(sum: BigDecimal,
                    shortcode: CurrencyShortcode,
                    date: LocalDate): BigDecimal {
        val currencyValue = currencyPairRepository
                .getLastCurrencyCoursePair(CurrencyShortcode.USD.toString(), shortcode.toString(), date)
                .close
        return sum.divide(currencyValue, 3, RoundingMode.HALF_UP)
    }

    fun isTransactionExceed(expenseCategory: ExpenseCategory, limit: LimitEntity): Boolean {
        return when (expenseCategory) {
            ExpenseCategory.SERVICE -> ZERO > limit.balanceService
            ExpenseCategory.PRODUCT -> ZERO > limit.balanceProduct
        }
    }

    fun convertTransactionsToUSD(groupedTransactions: Map<CurrencyShortcode, List<TransactionLimitEntity>>,
                                 executorService: ExecutorService)
            : List<CompletableFuture<List<CreateTransactionResponseDto>>> {
        return groupedTransactions.map { (shortcode, transactions) ->
            CompletableFuture.supplyAsync({
                val transformedTransactions: List<CreateTransactionResponseDto>
                if (shortcode != CurrencyShortcode.USD) {
                    transformedTransactions = transactions.map { dto ->
                        val transactionResponseDto = transactionMapper.convertToCreateTransactionResponseDto(dto)
                        transactionResponseDto.sum = getSumInUSD(dto.sum, dto.currencyShortcode, dto.datetime.toLocalDate())
                        transactionResponseDto.currencyShortcode = CurrencyShortcode.USD
                        transactionResponseDto
                    }
                    transformedTransactions
                } else {
                    transformedTransactions = transactions.map { dto ->
                        transactionMapper.convertToCreateTransactionResponseDto(dto)
                    }
                    transformedTransactions
                }
            }, executorService)
        }

    }
}
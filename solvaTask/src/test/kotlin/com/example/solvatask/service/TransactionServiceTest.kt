package com.example.solvatask.service

import com.example.solvatask.config.integration.CurrencyFeignClient
import com.example.solvatask.dto.CreateTransactionRequestDto
import com.example.solvatask.entity.CurrencyPairEntity
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.entity.TransactionEntity
import com.example.solvatask.entity.TransactionLimitEntity
import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.example.solvatask.mapper.CurrencyMapper
import com.example.solvatask.mapper.LimitMapper
import com.example.solvatask.mapper.TransactionMapper
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.repository.TransactionLimitRepository
import com.example.solvatask.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TransactionServiceTest {

    private val limitRepository: LimitRepository = mockk();
    private val transactionRepository: TransactionRepository = mockk();
    private val currencyPairRepository: CurrencyPairRepository = mockk();
    private val transactionLimitRepository: TransactionLimitRepository = mockk();
    private val currencyFeignClient: CurrencyFeignClient = mockk();
    private val transactionMapper = TransactionMapper()
    private val currencyMapper = CurrencyMapper()
    private val currencyCourseService = CurrencyCourseService(currencyPairRepository, currencyMapper, currencyFeignClient)
    private val limitMapper = LimitMapper()
    private val limitService = LimitService(limitRepository, limitMapper)
    private val transactionService = TransactionService(transactionMapper,
            transactionRepository, transactionLimitRepository, limitRepository, limitService, currencyPairRepository,
            currencyCourseService)


    @Test
    fun testGetTransactionLimitExceedUSD() {
        val bankAccount = "11"
        val transaction1 = TransactionLimitEntity(
                accountFrom = bankAccount,
                accountTo = "22",
                sum = BigDecimal(1000),
                currencyShortcode = CurrencyShortcode.KZT,
                expenseCategory = ExpenseCategory.SERVICE,
                datetime = LocalDateTime.now(),
                limitExceed = true,
                limitSum = BigDecimal(1000),
                limitDatetime = LocalDateTime.now(),
                limitCurrencyShortcode = CurrencyShortcode.USD
        )
        val transaction2 = TransactionLimitEntity(
                accountFrom = bankAccount,
                accountTo = "22",
                sum = BigDecimal(1000),
                currencyShortcode = CurrencyShortcode.RUB,
                expenseCategory = ExpenseCategory.SERVICE,
                datetime = LocalDateTime.now(),
                limitExceed = true,
                limitSum = BigDecimal(1000),
                limitDatetime = LocalDateTime.now(),
                limitCurrencyShortcode = CurrencyShortcode.USD
        )
        every { transactionLimitRepository.findTransactionsLimits(bankAccount) } returns
                mutableListOf(transaction1,
                        transaction2)
        every { currencyPairRepository.getLastCurrencyCoursePair(any(), any(), any()) } returns
                CurrencyPairEntity(
                        shortcodeFrom = CurrencyShortcode.USD,
                        shortcodeTo = CurrencyShortcode.KZT,
                        date = LocalDate.now(),
                        close = BigDecimal(500)
                )
        val result = transactionService.getTransactionsExceedInUSD(bankAccount)
        verify(exactly = 1) { transactionLimitRepository.findTransactionsLimits(bankAccount) }
        verify(exactly = 2) { currencyPairRepository.getLastCurrencyCoursePair(any(), any(), any()) }
        Assertions.assertNotNull(result)
        Assertions.assertEquals(2, result.size)
        //test converting sum in USD
        Assertions.assertEquals(BigDecimal(2), result[0].sum.stripTrailingZeros())
    }

    @Test
    fun testCreateTransactionLimitExceedFalse() {
        val accountFrom = "11"
        val accountTo = "22"
        val request = CreateTransactionRequestDto(
                accountFrom,
                accountTo,
                BigDecimal(500),
                CurrencyShortcode.USD,
                ExpenseCategory.SERVICE)

        val limitEntity1 = LimitEntity(
                bankAccount = accountFrom,
                limitSum = BigDecimal(1000),
                balanceService = BigDecimal(1000),
                balanceProduct = BigDecimal(1000),
                datetime = LocalDateTime.now(),
                currencyShortcode = CurrencyShortcode.USD
        )

        every { limitRepository.getLastLimit(accountFrom) } returns Optional.of(limitEntity1);
        every { limitRepository.save(any()) } returns limitEntity1
        every { transactionRepository.save(any()) } returns
                TransactionEntity(
                        accountFrom = accountFrom,
                        accountTo = accountTo,
                        sum = BigDecimal(1100),
                        currencyShortcode = CurrencyShortcode.USD,
                        expenseCategory = ExpenseCategory.SERVICE,
                        datetime = LocalDateTime.now(),
                        limitExceed = false
                )


        val result = transactionService.createTransaction(request)

        verify(exactly = 1) { limitRepository.getLastLimit(accountFrom) }
        verify(exactly = 1) { limitRepository.save(any()) }
        verify(exactly = 1) { transactionRepository.save(any()) }
        Assertions.assertEquals(accountFrom, result.accountFrom)
        Assertions.assertEquals(false, result.limitExceed)
    }

    @Test
    fun testCreateTransactionLimitExceedTrue() {
        val accountFrom = "11"
        val accountTo = "22"
        val request = CreateTransactionRequestDto(
                accountFrom,
                accountTo,
                BigDecimal(1100),
                CurrencyShortcode.USD,
                ExpenseCategory.SERVICE)

        val limitEntity1 = LimitEntity(
                bankAccount = accountFrom,
                limitSum = BigDecimal(1000),
                balanceService = BigDecimal(1000),
                balanceProduct = BigDecimal(1000),
                datetime = LocalDateTime.now(),
                currencyShortcode = CurrencyShortcode.USD
        )
        every { limitRepository.getLastLimit(accountFrom) } returns Optional.of(limitEntity1);
        every { limitRepository.save(any()) } returns limitEntity1
        every { transactionRepository.save(any()) } returns
                TransactionEntity(
                        accountFrom = accountFrom,
                        accountTo = accountTo,
                        sum = BigDecimal(1100),
                        currencyShortcode = CurrencyShortcode.USD,
                        expenseCategory = ExpenseCategory.SERVICE,
                        datetime = LocalDateTime.now(),
                        limitExceed = false
                )

        val result = transactionService.createTransaction(request);

        verify(exactly = 1) { limitRepository.getLastLimit(accountFrom) }
        verify(exactly = 1) { limitRepository.save(any()) }
        verify(exactly = 1) { transactionRepository.save(any()) }
        Assertions.assertEquals(accountFrom, result.accountFrom)
        Assertions.assertEquals(true, result.limitExceed)
    }
}
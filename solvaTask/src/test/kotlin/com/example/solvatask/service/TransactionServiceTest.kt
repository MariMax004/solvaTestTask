package com.example.solvatask.service

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import com.example.solvatask.mapper.LimitMapper
import com.example.solvatask.mapper.TransactionMapper
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.entity.TransactionEntity
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.repository.TransactionRepository
import com.example.solvatask.dto.CreateTransactionRequestDto
import com.example.solvatask.repository.TransactionLimitRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class TransactionServiceTest {

    val limitRepository: LimitRepository = mockk();
    val transactionRepository: TransactionRepository = mockk();
    val currencyPairRepository: CurrencyPairRepository = mockk();
    val transactionLimitRepository: TransactionLimitRepository = mockk();
    val transactionMapper = TransactionMapper()
    val limitMapper = LimitMapper()
    val limitService = LimitService(limitRepository, limitMapper)
    val transactionService = TransactionService(transactionMapper,
            transactionRepository, transactionLimitRepository, limitRepository, limitService, currencyPairRepository)

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


        val result = transactionService.createTransaction(request);

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
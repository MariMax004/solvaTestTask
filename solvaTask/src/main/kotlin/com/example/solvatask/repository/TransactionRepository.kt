package com.example.solvatask.repository

import com.example.solvatask.model.TransactionEntity
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, Long> {
    @Query(value = "SELECT t.id,\n" +
            "       t.account_from,\n" +
            "       t.account_to,\n" +
            "       t.datetime,\n" +
            "       t.limit_exceed,\n" +
            "       t.sum,\n" +
            "       t.currency_shortcode,\n" +
            "       t.expense_category,\n" +
            "       cl.limit_sum,\n" +
            "       cl.datetime,\n" +
            "       cl.currency_shortcode " +
            "FROM transaction t " +
            "LEFT JOIN client_limit cl ON t.account_from = cl.bank_account " +
            "WHERE t.limit_exceed = true " +
            "AND cl.datetime = (SELECT MAX(cl1.datetime) FROM client_limit cl1 WHERE cl1.datetime <= t.datetime) " +
            "AND t.account_from = :bankAccount", nativeQuery = true)
    fun findTransactionsWithLimitExceed(@Param("bankAccount") bankAccount: String): List<Tuple>
}
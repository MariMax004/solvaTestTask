package com.example.solvatask.repository

import com.example.solvatask.entity.TransactionLimitEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionLimitRepository : JpaRepository<TransactionLimitEntity, Long>{
    @Query(value = "SELECT t.id, " +
            "       t.account_from, " +
            "       t.account_to, " +
            "       t.datetime, " +
            "       t.limit_exceed, " +
            "       t.sum, " +
            "       t.currency_shortcode, " +
            "       t.expense_category, " +
            "       cl.limit_sum, " +
            "       cl.datetime as limit_datetime, " +
            "       cl.currency_shortcode as limit_currency_shortcode " +
            "FROM transaction t " +
            "         LEFT JOIN client_limit cl ON t.account_from = cl.bank_account " +
            "WHERE t.limit_exceed = true " +
            "  AND cl.datetime = (SELECT MAX(cl1.datetime) FROM client_limit cl1 WHERE cl1.datetime <= t.datetime) " +
            "  AND t.account_from = :bankAccount", nativeQuery = true)
    fun findTransactionsLimits(@Param("bankAccount") bankAccount: String): List<TransactionLimitEntity>
}
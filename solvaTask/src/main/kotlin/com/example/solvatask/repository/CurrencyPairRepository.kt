package com.example.solvatask.repository

import com.example.solvatask.entity.CurrencyPairEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface CurrencyPairRepository : JpaRepository<CurrencyPairEntity, String> {
    @Query(value = "SELECT cp.id, " +
            "       cp.shortcode_from, " +
            "       cp.shortcode_to, " +
            "       cp.close, " +
            "       cp.date " +
            "FROM currency_pair cp " +
            "WHERE cp.shortcode_from = :shortcodeFrom " +
            "  AND cp.shortcode_to = :shortcodeTo " +
            "  AND cp.date <= :date " +
            "ORDER BY cp.date DESC " +
            "LIMIT 1", nativeQuery = true)
    fun getLastCurrencyCoursePair(
            @Param("shortcodeFrom") shortcodeFrom: String,
            @Param("shortcodeTo") shortcodeTo: String,
            @Param("date") date: LocalDate): CurrencyPairEntity
}
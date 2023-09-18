package com.example.solvatask.repository

import com.example.solvatask.model.CurrencyPairEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CurrencyPairRepository : JpaRepository<CurrencyPairEntity, String> {
    @Query(value = "SELECT *\n" +
            "FROM currency_pair cp\n" +
            "WHERE cp.shortcode_from =:shortcodeFrom\n" +
            "  AND cp.shortcode_to=:shortcodeTo\n" +
            "order by cp.date desc\n" +
            "limit 1", nativeQuery = true)
    fun getLastCurrencyCoursePair(@Param("shortcodeFrom") shortcodeFrom: String,
                        @Param("shortcodeTo") shortcodeTo: String?): CurrencyPairEntity?
}
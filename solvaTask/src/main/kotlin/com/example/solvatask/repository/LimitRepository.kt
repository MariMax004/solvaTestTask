package com.example.solvatask.repository

import com.example.solvatask.model.LimitEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LimitRepository : JpaRepository<LimitEntity, Long> {
    @Query(value = "SELECT *FROM client_limit where bank_account = :account order by id desc limit 1", nativeQuery = true)
    fun getLastLimit(@Param("account") account: String?): Optional<LimitEntity>
    fun getByBankAccount(account: String?): List<LimitEntity>
}
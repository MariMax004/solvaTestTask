package com.example.solvatask.repository

import com.example.solvatask.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, Long> {
}
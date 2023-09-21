package com.example.solvatask.service

import com.example.solvatask.mapper.LimitMapper
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.dto.CreateLimitRequestDto
import com.example.solvatask.dto.CreateLimitResponseDto
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LimitService(private val limitRepository: LimitRepository, private val limitMapper: LimitMapper) {

    fun getClientLimits(bankAccount: String): List<CreateLimitResponseDto> {
        val limits = limitRepository.getByBankAccount(bankAccount)
        if (limits.isEmpty()) {
            throw EntityNotFoundException("No limits found for the given bank account $bankAccount")
        }

        return limits
                .map { limitMapper.convertToCreateLimitResponseDto(it) }
                .sortedBy { it.datetime }
    }

    @Transactional
    fun createClientLimit(limitRequest: CreateLimitRequestDto): CreateLimitResponseDto {
        val limit = limitMapper.convertToLimit(limitRequest.bankAccount,
                limitRequest.limitSum
        )
        limitRepository.save(limit)
        return limitMapper.convertToCreateLimitResponseDto(limit)
    }

    fun createClientLimit(bankAccount: String, limitSum: BigDecimal): LimitEntity {
        return limitMapper.convertToLimit(bankAccount, limitSum)
    }
}



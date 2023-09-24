package com.example.solvatask.service

import com.example.solvatask.dto.CreateLimitRequestDto
import com.example.solvatask.dto.CreateLimitResponseDto
import com.example.solvatask.entity.LimitEntity
import com.example.solvatask.mapper.LimitMapper
import com.example.solvatask.repository.LimitRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class LimitService(val limitRepository: LimitRepository,
                   val limitMapper: LimitMapper) {

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



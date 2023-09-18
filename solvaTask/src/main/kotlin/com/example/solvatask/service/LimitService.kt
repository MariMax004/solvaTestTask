package com.example.solvatask.service

import com.example.solvatask.mapper.LimitMapper
import com.example.solvatask.model.LimitEntity
import com.example.solvatask.repository.LimitRepository
import com.example.solvatask.request.CreateLimitRequestDto
import com.example.solvatask.response.CreateLimitResponseDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LimitService(private val limitRepository: LimitRepository, private val limitMapper: LimitMapper) {

    fun getClientLimits(bankAccount: String): List<CreateLimitResponseDto> {
        return limitRepository.getByBankAccount(bankAccount)
                .map { limitMapper.convertToCreateLimitResponseDto(it) }
                .sortedBy { it.datetime }
    }

    @Transactional
    fun createClientLimit(limitRequest: CreateLimitRequestDto): CreateLimitResponseDto {
        val limit = limitMapper.convertToLimit(limitRequest.bankAccount, limitRequest.limitSum)
        limitRepository.save(limit)
        return limitMapper.convertToCreateLimitResponseDto(limit)
    }

    @Transactional
    fun createClientLimit(bankAccount: String?, limitSum: BigDecimal?): LimitEntity {
        val limit = limitMapper.convertToLimit(bankAccount, limitSum)
        limitRepository.save(limit)
        return limit
    }
}



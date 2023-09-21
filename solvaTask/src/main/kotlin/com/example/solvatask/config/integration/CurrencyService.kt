package com.example.solvatask.config.integration

import com.example.solvatask.config.integration.CurrencyFeignClient
import com.example.solvatask.response.CurrencyCourseModel
import org.springframework.stereotype.Service

@Service
class CurrencyService(val currencyFeignClient: CurrencyFeignClient) {

    fun getCurrencyCourse(): CurrencyCourseModel {
        val currencyCourseModel = currencyFeignClient.getCurrencyCourse()
        return currencyCourseModel
    }
}
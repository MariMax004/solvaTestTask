package com.example.solvatask.service

import com.example.solvatask.config.integration.CurrencyFeignClient
import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.mapper.CurrencyMapper
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.response.CurrencyCourseModel
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CurrencyCourseService(val currencyPairRepository: CurrencyPairRepository,
                            val currencyMapper: CurrencyMapper,
                            val currencyFeignClient: CurrencyFeignClient) {
    fun getCurrencyCourse(from: CurrencyShortcode, to: CurrencyShortcode): CurrencyCourseModel {
        return currencyFeignClient.getCurrencyCourse(from, to)
    }

    fun saveCurrencyCourse(model: CurrencyCourseModel) {
        val currencyPair = currencyMapper.convertToCurrencyPair(model)
        currencyPair.id = generateCurrencyPairId(currencyPair.shortcodeFrom,
                currencyPair.shortcodeTo, currencyPair.date)
        currencyPairRepository.save(currencyPair)
    }

    fun generateCurrencyPairId(from: CurrencyShortcode,
                               to: CurrencyShortcode,
                               date: LocalDate): String {
        return "${date}${from}-${to}"
    }


    fun generateShortcodePairs(): List<Pair<CurrencyShortcode, CurrencyShortcode>> {
        val values = CurrencyShortcode.values().filter { it != CurrencyShortcode.USD }
        val pairs = mutableListOf<Pair<CurrencyShortcode, CurrencyShortcode>>()

        for (currency2 in values) {
            pairs.add(Pair(CurrencyShortcode.USD, currency2))
        }

        return pairs
    }
}

package com.example.solvatask.service

import com.example.solvatask.config.CurrencyConfig
import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.mapper.CurrencyMapper
import com.example.solvatask.model.CurrencyPairEntity
import com.example.solvatask.repository.CurrencyPairRepository
import com.example.solvatask.resttemplatemodel.CurrencyCourseModel
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CurrencyCourseService(private val currencyConfig: CurrencyConfig,
                            private val currencyPairRepository: CurrencyPairRepository,
                            private val currencyMapper: CurrencyMapper,
                            private val currencyRestTemplate: RestTemplate) {
    fun getCurrencyCourse(from: CurrencyShortcode, to: CurrencyShortcode): CurrencyCourseModel? {
        val urlFromTo = "${currencyConfig.url}?symbol=${from}/${to}:Huobi,&interval=1h&apikey=${currencyConfig.apiKey}"
        return currencyRestTemplate.getForEntity(urlFromTo, CurrencyCourseModel::class.java).body
    }

    fun saveCurrencyCourse(model: CurrencyCourseModel?) {
        val currencyPair = currencyMapper.convertToCurrencyPair(model)
        currencyPair.id = generateCurrencyPairId(currencyPair)
        currencyPairRepository.save(currencyPair)
    }

    fun generateCurrencyPairId(currencyPair: CurrencyPairEntity): String {
        return "${currencyPair.date}${currencyPair.shortcodeFrom}-${currencyPair.shortcodeTo}"
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

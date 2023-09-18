package com.example.solvatask.mapper

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.model.CurrencyPairEntity
import com.example.solvatask.resttemplatemodel.CurrencyCourseModel
import org.springframework.stereotype.Component

@Component
class CurrencyMapper {
    fun convertToCurrencyPair(model: CurrencyCourseModel?): CurrencyPairEntity {
        var currencyPair = CurrencyPairEntity()
        currencyPair.date = model?.pair?.values?.get(0)?.datetime
        val shortcodePair = model?.pair?.meta?.symbol
        currencyPair.shortcodeFrom = parseCurrencyPair(shortcodePair)?.first
        currencyPair.shortcodeTo = parseCurrencyPair(shortcodePair)?.second
        currencyPair.close = model?.pair?.values?.get(0)?.close
        return currencyPair
    }

    fun parseCurrencyPair(pair: String?): Pair<CurrencyShortcode, CurrencyShortcode>? {
        val currencies = pair?.split("/")
        if (currencies?.size == 2) {
            val currency1 = CurrencyShortcode.valueOf(currencies[0].trim())
            val currency2 = CurrencyShortcode.valueOf(currencies[1].trim())
            return Pair(currency1, currency2)
        }
        return null
    }
}
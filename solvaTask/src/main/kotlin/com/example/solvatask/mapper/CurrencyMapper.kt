package com.example.solvatask.mapper

import com.example.solvatask.entity.CurrencyPairEntity
import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.response.CurrencyCourseModel
import org.springframework.stereotype.Component

@Component
class CurrencyMapper {
    fun convertToCurrencyPair(model: CurrencyCourseModel): CurrencyPairEntity {
        val shortcodePairParsed = parseCurrencyPair(model.meta.symbol)
        return CurrencyPairEntity(
                shortcodeFrom = shortcodePairParsed.first,
                shortcodeTo = shortcodePairParsed.second,
                date = model.values[0].datetime.toLocalDate(),
                close = model.values[0].close
        )
    }

    fun parseCurrencyPair(pair: String): Pair<CurrencyShortcode, CurrencyShortcode> {
        val currencies = pair.split("/")
        val currencyFrom = CurrencyShortcode.valueOf(currencies[0].trim())
        val currencyTo = CurrencyShortcode.valueOf(currencies[1].trim())
        return Pair(currencyFrom, currencyTo)
    }
}
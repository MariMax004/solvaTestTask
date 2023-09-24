package com.example.solvatask.config.integration

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.response.CurrencyCourseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "currency-feign-client", url = "\${currency.url}")
interface CurrencyFeignClient {

    @GetMapping("?symbol={from}/{to}:Huobi,&interval=1h&apikey=\${currency.api-key}")
    fun getCurrencyCourse(@PathVariable from: CurrencyShortcode,
                          @PathVariable to: CurrencyShortcode): CurrencyCourseModel
}

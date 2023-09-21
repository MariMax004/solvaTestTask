package com.example.solvatask.config.integration

import com.example.solvatask.response.CurrencyCourseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "currency-service", url = "https://api.twelvedata.com/time_series")
interface CurrencyFeignClient {

    @GetMapping("?symbol=USD/KZT:Huobi,&interval=1h&apikey=8a77e4eece4941a399336c741bc7cbf4")
    fun getCurrencyCourse(): CurrencyCourseModel
}
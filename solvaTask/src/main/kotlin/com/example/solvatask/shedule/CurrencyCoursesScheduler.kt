package com.example.solvatask.shedule

import com.example.solvatask.service.CurrencyCourseService
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CurrencyCoursesScheduler(val currencyCourseService: CurrencyCourseService) {

    val pairs = currencyCourseService.generateShortcodePairs()

    @Scheduled(cron = "0 0 8 * * ?")
    @Bean
    fun initCurrencyCourse() {
        for (pair in pairs) {
            val model = currencyCourseService.getCurrencyCourse(pair.first, pair.second);
            model?.let { currencyCourseService.saveCurrencyCourse(model) }
        }
    }
}
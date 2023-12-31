package com.example.solvatask.shedule

import com.example.solvatask.service.CurrencyCourseService
import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class CurrencyCoursesScheduler(val currencyCourseService: CurrencyCourseService) {

    @Scheduled(cron = "0 0 8 * * ?")
    @PostConstruct
    fun initCurrencyKzt() {
        val pairs = currencyCourseService.generateShortcodePairs()
        for (pair in pairs) {
            val model = currencyCourseService.getCurrencyCourse(pair.first, pair.second);
            currencyCourseService.saveCurrencyCourse(model)
        }
    }
}
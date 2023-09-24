package com.example.solvatask.shedule

import com.example.solvatask.service.CurrencyCourseService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CurrencyCoursesScheduler(val currencyCourseService: CurrencyCourseService) {

    val pairs = currencyCourseService.generateShortcodePairs()

    @Scheduled(cron = "0 0 8 * * ?")
    @PostConstruct
    fun initCurrencyKzt() {
        for (pair in pairs) {
            val model = currencyCourseService.getCurrencyCourse(pair.first, pair.second);
            model?.let { currencyCourseService.saveCurrencyCourse(model) }
        }
    }
}
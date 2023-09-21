package com.example.solvatask.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class CurrencyRestTemplateConfig(val currencyConfig: CurrencyConfig) {
    @Bean
    fun currencyRestTemplate(): RestTemplate {
        val clientHttpRequestFactory = SimpleClientHttpRequestFactory()
        clientHttpRequestFactory.setConnectTimeout(currencyConfig.connectTimeout)
        clientHttpRequestFactory.setReadTimeout(currencyConfig.readTimeout)
        return RestTemplate(clientHttpRequestFactory)
    }
}
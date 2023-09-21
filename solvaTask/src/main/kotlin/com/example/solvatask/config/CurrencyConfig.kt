package com.example.solvatask.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "currency")
data class CurrencyConfig(
        var apiKey: String = "",
        var url: String = "",
        val connectTimeout: Int = 30000,
        val readTimeout: Int = 30000
)

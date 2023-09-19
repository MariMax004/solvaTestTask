package com.example.solvatask.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "currency")
data class CurrencyConfig(
        var apiKey: String = "",
        var url: String = "",
        val connectTimeout: Long = 0,
        val readTimeout: Long = 0
)

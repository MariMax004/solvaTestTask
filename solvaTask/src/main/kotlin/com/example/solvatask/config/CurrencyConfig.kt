package com.example.solvatask.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "currency")
class CurrencyConfig(
        var apiKey: String = "",
        var url: String = "",
        var connectTimeout: Long = 30000L,
        var readTimeout: Long = 30000L
)
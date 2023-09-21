package com.example.solvatask

import com.example.solvatask.config.CurrencyConfig
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(CurrencyConfig::class)
class SolvaTaskApplication

fun main(args: Array<String>) {
    runApplication<SolvaTaskApplication>(*args)
}

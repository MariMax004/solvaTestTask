package com.example.solvatask

import com.example.solvatask.config.CurrencyConfig
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(FeignClientsConfiguration::class)
@ImportAutoConfiguration(FeignAutoConfiguration::class)
@EnableFeignClients(basePackages = ["com.example.solvatask"])
@EnableConfigurationProperties(CurrencyConfig::class)
class SolvaTaskApplication

fun main(args: Array<String>) {
    runApplication<SolvaTaskApplication>(*args)
}

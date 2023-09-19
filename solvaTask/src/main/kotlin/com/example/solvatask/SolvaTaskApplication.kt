package com.example.solvatask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class SolvaTaskApplication

fun main(args: Array<String>) {
    runApplication<SolvaTaskApplication>(*args)
}

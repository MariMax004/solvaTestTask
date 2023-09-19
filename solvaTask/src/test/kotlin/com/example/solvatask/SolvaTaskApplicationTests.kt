package com.example.solvatask

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations = ["classpath:application.properties"])
class SolvaTaskApplicationTests {

    @Test
    fun contextLoads() {
    }

}

package com.example.solvatask.controller

import com.example.solvatask.request.CreateLimitRequestDto
import com.example.solvatask.response.CreateLimitResponseDto
import com.example.solvatask.service.LimitService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/limits")
class LimitController(private val limitService: LimitService) {

    @GetMapping("/{account}")
    fun getClientLimits(@PathVariable account: String): ResponseEntity<List<CreateLimitResponseDto>> {
        return ResponseEntity.ok(limitService.getClientLimits(account))
    }

    @PostMapping("/")
    fun createClientLimit(@RequestBody limitRequest: CreateLimitRequestDto): ResponseEntity<CreateLimitResponseDto> {
        return ResponseEntity.ok(limitService.createClientLimit(limitRequest))
    }
}
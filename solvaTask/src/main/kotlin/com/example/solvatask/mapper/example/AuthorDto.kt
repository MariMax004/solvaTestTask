package com.example.solvatask.mapper.example

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AuthorDto {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("surname")
    var surname: String? = null

    @JsonProperty("birth")
    @JsonFormat(pattern = "MM/dd/yyyy")
    var birthDate: Date? = null
}
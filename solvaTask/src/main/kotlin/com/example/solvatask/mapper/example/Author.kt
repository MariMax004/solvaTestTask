package com.example.solvatask.mapper.example

import java.util.*
import kotlin.collections.ArrayList

class Author {
    var id: Int? = null

    var name: String? = null

    var surname: String? = null

    var birthDate: Date? = null

    constructor()

    constructor(id: Int, name: String, surname: String, birthDate: Date) {
        this.id = id
        this.name = name
        this.surname = surname
        this.birthDate = birthDate
    }
}
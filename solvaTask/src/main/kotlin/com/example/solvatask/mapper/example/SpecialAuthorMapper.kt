package com.example.solvatask.mapper.example

import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class SpecialAuthorMapper {
    abstract fun authorToSpecialAuthorDto(
            author: Author
    ): SpecialAuthorDto
}
package com.example.solvatask.model

import com.example.solvatask.enums.CurrencyShortcode
import jakarta.persistence.*
import lombok.Getter
import java.math.BigDecimal
import java.time.LocalDate

@Getter
@lombok.Setter
@Entity
@Table(name = "currency_pair")
class CurrencyPairEntity {

    @Id
    var id: String = ""

    @Column(name = "shortcode_from")
    @Enumerated(EnumType.STRING)
    var shortcodeFrom: CurrencyShortcode? = null

    @Column(name = "shortcode_to")
    @Enumerated(EnumType.STRING)
    var shortcodeTo: CurrencyShortcode? = null

    @Column(name = "date")
    var date: LocalDate? = null

    @Column(name = "close")
    var close: BigDecimal? = null
}
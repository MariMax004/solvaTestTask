package com.example.solvatask.model

import com.example.solvatask.enums.CurrencyShortcode
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Getter
@Setter
@Entity
@Table(name = "client_limit")
class LimitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "limit_seq")
    @SequenceGenerator(name = "limit_seq", allocationSize = 1, sequenceName = "limit_seq")
    val id: Long = 1L

    @Column(name = "limit_sum")
    var limitSum: BigDecimal? = null

    @Column(name = "balance_product")
    var balanceProduct: BigDecimal? = null

    @Column(name = "balance_service")
    var balanceService: BigDecimal? = null

    @Column(name = "bank_account")
    var bankAccount: String? = null

    var datetime: LocalDateTime? = null

    @Column(name = "currency_shortcode")
    @Enumerated(EnumType.STRING)
    var currencyShortcode: CurrencyShortcode? = null
}

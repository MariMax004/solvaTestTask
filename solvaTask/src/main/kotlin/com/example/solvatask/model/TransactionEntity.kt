package com.example.solvatask.model

import com.example.solvatask.enums.CurrencyShortcode
import com.example.solvatask.enums.ExpenseCategory
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.math.BigDecimal
import java.util.*

@Getter
@Setter
@Entity
@Table(name = "transaction")
class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", allocationSize = 1, sequenceName = "transaction_seq")
    var id: Long? = null

    @Column(name = "account_from")
    var accountFrom: String? = null

    @Column(name = "account_to")
    var accountTo: String? = null

    @Column(name = "sum")
    var sum: BigDecimal? = null

    @Column(name = "currency_shortcode")
    @Enumerated(EnumType.STRING)
    var currencyShortcode: CurrencyShortcode? = null

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    var expenseCategory: ExpenseCategory? = null

    var datetime: Date? = null

    @Column(name = "limit_exceed")
    var limitExceed: Boolean? = null
}
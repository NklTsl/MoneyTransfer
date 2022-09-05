package com.money.transfer.model

import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "transaction_history")
data class TransactionHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "transfer_amount")
    var transferAmount: BigDecimal = ZERO,
    @JoinColumn(name = "transfer_from_account")
    @ManyToOne(targetEntity = Account::class)
    var transferFromAccount: Account,
    @JoinColumn(name = "transfer_to_account")
    @ManyToOne(targetEntity = Account::class)
    var transferToAccount: Account,
    @Column(name = "transaction_time")
    var transactionTime: Date
) {
    constructor() : this(0, BigDecimal.valueOf(0.0), Account(), Account(), Date()) {

    }
}

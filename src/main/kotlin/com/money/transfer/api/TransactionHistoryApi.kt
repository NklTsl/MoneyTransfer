package com.money.transfer.api

import com.money.transfer.model.TransactionHistory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/transaction-history")
interface TransactionHistoryApi {

    @GetMapping("/{id}/debit")
    fun getDebitTransactions(@PathVariable id: Long): List<TransactionHistory>

    @GetMapping("/{id}/credit")
    fun getCreditTransactions(@PathVariable id: Long): List<TransactionHistory>

    @GetMapping("/{id}/history")
    fun getPaymentHistory(@PathVariable id: Long): List<TransactionHistory>
}

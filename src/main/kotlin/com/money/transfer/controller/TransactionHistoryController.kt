package com.money.transfer.controller

import com.money.transfer.api.TransactionHistoryApi
import com.money.transfer.model.Account
import com.money.transfer.model.TransactionHistory
import com.money.transfer.service.ITransactionHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionHistoryController : TransactionHistoryApi {

    @Autowired
    lateinit var transactionHistoryService: ITransactionHistoryService

    override fun getDebitTransactions(id: Long) = transactionHistoryService.getAllDebitTransactions(Account(id))

    override fun getCreditTransactions(id: Long) = transactionHistoryService.getAllCreditTransactions(Account(id))

    override fun getPaymentHistory(id: Long) = transactionHistoryService.getPaymentHistory(Account(id))

}
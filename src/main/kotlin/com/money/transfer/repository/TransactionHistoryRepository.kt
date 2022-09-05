package com.money.transfer.repository

import com.money.transfer.model.Account
import com.money.transfer.model.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionHistoryRepository : JpaRepository<TransactionHistory, Long> {
    fun getById(id: Long): Optional<TransactionHistory>
    fun getTransactionHistoriesByTransferFromAccount(fromAcc: Account): List<TransactionHistory>
    fun getTransactionHistoriesByTransferToAccount(toAcc: Account): List<TransactionHistory>
    fun getTransactionHistoriesByTransferToAccountOrTransferFromAccount(toAcc: Account, fromAcc: Account): List<TransactionHistory>
}
package com.money.transfer.service

import com.money.transfer.model.Account
import com.money.transfer.model.TransactionHistory
import com.money.transfer.repository.TransactionHistoryRepository
import com.money.transfer.utils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

interface ITransactionHistoryService {
    fun getAll(): List<TransactionHistory>
    fun getAllDebitTransactions(fromAcc: Account): List<TransactionHistory>
    fun getAllCreditTransactions(toAcc: Account): List<TransactionHistory>
    fun getPaymentHistory(account: Account): List<TransactionHistory>
    fun create(from: Account, to: Account, amount: BigDecimal): TransactionHistory
}

@Service
class TransactionHistoryService : ITransactionHistoryService {

    @Autowired
    private lateinit var transactionHistoryRepository: TransactionHistoryRepository

    private val log by logger()

    @Transactional(readOnly = true)
    override fun getAll(): List<TransactionHistory> {
        log.info("Retrieving all the transaction history");
        return transactionHistoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    override fun getAllDebitTransactions(fromAcc: Account): List<TransactionHistory> {
        log.info("Retrieving all the debit transactions from account $fromAcc.id");
        return transactionHistoryRepository.getTransactionHistoriesByTransferFromAccount(fromAcc);
    }

    @Transactional(readOnly = true)
    override fun getAllCreditTransactions(toAcc: Account): List<TransactionHistory> {
        log.info("Retrieving all the credit transactions to account $toAcc.id");
        return transactionHistoryRepository.getTransactionHistoriesByTransferToAccount(toAcc);
    }

    @Transactional(readOnly = true)
    override fun getPaymentHistory(account: Account): List<TransactionHistory> {
        log.info("Retrieving all the payment transactions for account $account.id");
        return transactionHistoryRepository.getTransactionHistoriesByTransferToAccountOrTransferFromAccount(
            account,
            account
        );
    }

    @Transactional
    override fun create(from: Account, to: Account, amount: BigDecimal): TransactionHistory {
        log.info("Transfer has been done from account ${from} to account ${to} with amount ${amount}");
        var transactionHistory = TransactionHistory(0, amount, from, to, Date());
        return transactionHistoryRepository.save(transactionHistory)
    }


}
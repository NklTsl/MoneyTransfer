package com.money.transfer.tests

import com.github.springtestdbunit.annotation.DatabaseOperation.CLEAN_INSERT
import com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import com.money.transfer.TransferApplicationTests
import com.money.transfer.exceptions.AccountNotFoundException
import com.money.transfer.exceptions.AccountServiceException
import com.money.transfer.exceptions.InsufficientFundsException
import com.money.transfer.integration.AccountServiceIT.Companion.DATASET
import com.money.transfer.service.AccountService
import com.money.transfer.service.TransactionHistoryService
import junit.framework.TestCase.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

@DatabaseSetup(type = CLEAN_INSERT, value = [DATASET])
@DatabaseTearDown(type = DELETE_ALL, value = [DATASET])
class AccountServiceTest : TransferApplicationTests() {

    @Autowired lateinit var accountService: AccountService
    @Autowired lateinit var transactionHistoryService: TransactionHistoryService
    @Test
    fun getAccountTest() {
        val id = 1L
        val account = accountService.get(id)
        assertEquals(id, account.id)
        assertEquals(100, account.balance.intValueExact())
    }

    @Test(expected = AccountNotFoundException::class)
    @Throws
    fun getAccountNotExistingIdTest() {
        val id = 63L
        val account = accountService.get(id)
        assertNull(account)
    }

    @Test
    fun createNewAccountTest() {
        val id = 7L
        val amount : BigDecimal = BigDecimal.valueOf(0);
        val account = accountService.create()
        assertEquals(id, account.id)
        assertEquals(amount, account.balance)
    }

    @Test
    fun addMoneyTest() {
        val id = 6L
        val amount : BigDecimal = BigDecimal.valueOf(67.0);
        accountService.create()
        val account = accountService.add(id, amount)
        assertEquals(id, account.id)
        assertEquals(amount, account.balance)
    }

    @Test(expected = AccountServiceException::class)
    fun addMoneyNegativeAmountTest() {
        accountService.add(6L, BigDecimal.valueOf(-100))
    }

    @Test(expected = AccountNotFoundException::class)
    fun addMoneyNotExistingIdTest() {
        accountService.add(17L, BigDecimal.valueOf(50))
    }


    @Test
    fun withdrawMoneyTest() {
        val id = 4L
        val account = accountService.withdraw(id, BigDecimal.valueOf(100))
        assertEquals(id, account.id)
        assertEquals(BigDecimal.valueOf(300.0), account.balance)
    }

    @Test
    fun withdrawAllMoneyTest() {
        val id = 1L
        val account = accountService.withdraw(id, BigDecimal.valueOf(100))
        assertEquals(id, account.id)
        assertEquals(BigDecimal.valueOf(0.0), account.balance)
    }


    @Test(expected = InsufficientFundsException::class)
    fun withdrawMoneyInsufficientFundsTest() {
        val id = 1L
        accountService.withdraw(id, BigDecimal.valueOf(150))
    }

    @Test(expected = AccountNotFoundException::class)
    fun withdrawMoneyNotExistingIdTest() {
        val id = 17L;
        accountService.withdraw(id, BigDecimal.valueOf(300));
    }

    @Test(expected = AccountServiceException::class)
    fun withdrawMoneyNegativeAmountTest() {
        accountService.withdraw( 2L, BigDecimal.valueOf(-100))
    }

    @Test
    fun transferMoneyTest() {
        val from = 4L
        val to = 2L
        val accountsPair = accountService.transfer(from, to, BigDecimal.valueOf(100))
        val transactionHistory = transactionHistoryService.getAll()
        assertEquals(from, accountsPair.from.id)
        assertEquals(BigDecimal.valueOf(300.0), accountsPair.from.balance)
        assertEquals(to, accountsPair.to.id)
        assertEquals(BigDecimal.valueOf(300.0), accountsPair.to.balance)
        assertNotNull(transactionHistory)
        assertEquals(1, transactionHistory.size)
    }

    @Test(expected = InsufficientFundsException::class)
    fun transferMoneyInsufficientFundsTest() {
        val accountsPair = accountService.transfer(1L, 2L, BigDecimal.valueOf(200))
    }

//    @Test
//    fun transferMoneyNotExistingIdTest() {
//        execRequestExpectedTest(patch(TRANSFER_MONEY, 1L, Long.MIN_VALUE, 200))
//    }
//
//    @Test
//    fun transferMoneyWrongAmountTypeTest() {
//        execRequestExpectedTest(patch(TRANSFER_MONEY, 4L, 1L, "abc"))
//    }
//
//    @Test
//    fun transferMoneyNegativeAmountTest() {
//        execRequestExpectedTest(patch(TRANSFER_MONEY, 4L, 1L, -100))
//    }
//
//    companion object {
//        const val DATASET = "classpath:datasets/accounts.xml"
//        const val PREFIX = "/account"
//        const val GET_ACCOUNT = "$PREFIX/{id}"
//        const val ADD_MONEY = "$PREFIX/{id}/add/{amount}"
//        const val WITHDRAW_MONEY = "$PREFIX/{id}/withdraw/{amount}"
//        const val TRANSFER_MONEY = "$PREFIX/{from}/transfer/{to}/{amount}"
//        val ACC_TYPE = jacksonTypeRef<Account>()
//        val ACC_PAIR_TYPE = jacksonTypeRef<AccountTransferPair>()
//    }
}

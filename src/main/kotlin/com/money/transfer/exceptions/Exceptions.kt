package com.money.transfer.exceptions

open class AccountServiceException(override val message: String) : RuntimeException()

class AccountNotFoundException: AccountServiceException("Account not found")

class AccountAlreadyExistException: AccountServiceException("Account exists")
class InsufficientFundsException: AccountServiceException("Insufficient funds")
package com.tva.cashcoach.infrastructure.persistence.repository.transaction

import com.tva.cashcoach.infrastructure.model.transaction.Transaction
import com.tva.cashcoach.infrastructure.model.transaction.TransactionDao
import java.util.Currency

class TransactionRepository(private val transactionDao: TransactionDao) {

    fun getById(id: Int): Transaction? {
        return transactionDao.getById(id)
    }

    fun getAll(wallet_id: String): List<Transaction> {
        return transactionDao.getAll(wallet_id)
    }

    fun getAllTransactions(wallet_id: String): List<Transaction> {
        return transactionDao.getAllTransactions(wallet_id)
    }

    fun getIncomesSum(wallet_id: String, currency: String): Double {
        return transactionDao.getIncomesSum(wallet_id, currency)
    }

    fun getExpensesSum(wallet_id: String, currency: String): Double {
        return transactionDao.getExpensesSum(wallet_id, currency)
    }

    fun insert(transaction: Transaction): Long {
        return transactionDao.insert(transaction)
    }

    fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    fun deleteById(id: Int) {
        transactionDao.deleteById(id)
    }

    fun update(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    fun wipeTable() {
        transactionDao.wipeTable()
    }
}
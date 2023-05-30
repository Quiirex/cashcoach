package com.tva.cashcoach.appcomponents.persistence.repository.transaction

import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao

class TransactionRepository(private val transactionDao: TransactionDao) {

//    val allIncomes: LiveData<List<Transaction>> = transactionDao.getAllIncomes()
//    val allExpenses: LiveData<List<Transaction>> = transactionDao.getAllExpenses()
//    val allTransactions: List<Transaction> = transactionDao.getAll()

    fun getAll(wallet_id: String): List<Transaction> {
        return transactionDao.getAll(wallet_id)
    }

    fun getIncomesSum(wallet_id: String): Double {
        return  transactionDao.getIncomesSum(wallet_id)
    }

    fun getExpensesSum(wallet_id: String): Double {
        return  transactionDao.getExpensesSum(wallet_id)
    }
    fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    fun update(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    fun wipeTable() {
        transactionDao.wipeTable()
    }
}
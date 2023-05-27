package com.tva.cashcoach.appcomponents.persistence.repository.transaction

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.income.Income
import com.tva.cashcoach.appcomponents.model.income.IncomeDao
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allIncomes: LiveData<List<Transaction>> = transactionDao.getAll()

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
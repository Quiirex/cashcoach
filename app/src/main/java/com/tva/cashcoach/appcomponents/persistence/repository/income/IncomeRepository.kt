package com.tva.cashcoach.appcomponents.persistence.repository.income

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.income.Income
import com.tva.cashcoach.appcomponents.model.income.IncomeDao

class IncomeRepository(private val incomeDao: IncomeDao) {

    val allIncomes: LiveData<List<Income>> = incomeDao.getAll()

    suspend fun insert(income: Income) {
        incomeDao.insert(income)
    }

    suspend fun delete(income: Income) {
        incomeDao.delete(income)
    }

    suspend fun update(income: Income) {
        incomeDao.update(income)
    }

    suspend fun wipeTable() {
        incomeDao.wipeTable()
    }
}
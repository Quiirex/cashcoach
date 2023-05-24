package com.tva.cashcoach.appcomponents.persistence.repository.expense

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.expense.Expense
import com.tva.cashcoach.appcomponents.model.expense.ExpenseDao

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAll()

    fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

    fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    fun wipeTable() {
        expenseDao.wipeTable()
    }
}
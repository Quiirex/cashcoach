package com.tva.cashcoach.appcomponents.model.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` ORDER BY id ASC")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE type = 'income'")
    fun getAllIncomes(): LiveData<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE type = 'expense'")
    fun getAllExpenses(): LiveData<List<Transaction>>
    @Query("SELECT * FROM `transaction` WHERE id = :id")
    fun getById(id: Int): Transaction?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update(transaction: Transaction)

    @Query("DELETE FROM `transaction`")
    fun wipeTable()
}
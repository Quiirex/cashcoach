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
    @Query("SELECT * FROM `transaction` WHERE wallet_id = :wallet_id ORDER BY id DESC")
    fun getAll(wallet_id: String): List<Transaction>

    @Query("SELECT SUM(value) FROM `transaction` WHERE type = 'income' AND wallet_id = :wallet_id")
    fun getIncomesSum(wallet_id: String): Double

    @Query("SELECT SUM(value) FROM `transaction` WHERE type = 'expense' AND wallet_id = :wallet_id")
    fun getExpensesSum(wallet_id: String): Double

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
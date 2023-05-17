package com.tva.cashcoach.appcomponents.model.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense ORDER BY id ASC")
    fun getAll(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("DELETE FROM expense")
    suspend fun wipeTable()
}
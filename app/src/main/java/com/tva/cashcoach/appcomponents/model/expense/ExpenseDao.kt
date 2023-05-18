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

    @Query("SELECT * FROM expense WHERE id = :id")
    fun getById(id: Long): Expense?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Query("DELETE FROM expense")
    fun wipeTable()
}
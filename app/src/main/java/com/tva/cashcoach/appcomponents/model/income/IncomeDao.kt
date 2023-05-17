package com.tva.cashcoach.appcomponents.model.income

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income ORDER BY id ASC")
    fun getAll(): LiveData<List<Income>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Update
    suspend fun update(income: Income)

    @Query("DELETE FROM income")
    suspend fun wipeTable()
}
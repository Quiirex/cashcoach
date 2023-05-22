package com.tva.cashcoach.appcomponents.model.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY id ASC")
    fun getAll(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: Int): Category?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Update
    fun update(category: Category)

    @Query("DELETE FROM category")
    fun wipeTable()
}
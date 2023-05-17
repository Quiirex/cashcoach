package com.tva.cashcoach.appcomponents.model.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM user")
    suspend fun wipeTable()
}
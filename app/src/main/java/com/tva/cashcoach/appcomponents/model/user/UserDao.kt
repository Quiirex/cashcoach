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

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Long): User?

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun getByUid(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM user")
    fun wipeTable()
}
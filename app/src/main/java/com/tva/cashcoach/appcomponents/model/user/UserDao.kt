package com.tva.cashcoach.appcomponents.model.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int): User?

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun getByUid(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("UPDATE user SET currency = :currency WHERE uid = :uid")
    fun updateCurrencyByUid(uid: String, currency: String)

    @Query("UPDATE user SET language = :language WHERE uid = :uid")
    fun updateLanguageByUid(uid: String, language: String)

    @Query("DELETE FROM user")
    fun wipeTable()
}
package com.tva.cashcoach.infrastructure.model.user

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

    fun getDefaultWalletId(uid: String): Int {
        return getByUid(uid)?.default_wallet_id ?: 0
    }

    @Query("UPDATE user SET default_wallet_id = :walletId WHERE uid = :uid")
    fun setDefaultWalletId(uid: String, walletId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User): Long

    @Delete
    fun delete(user: User)

    @Query("UPDATE user SET currency = :currency WHERE uid = :uid")
    fun updateCurrencyByUid(uid: String, currency: String)

    @Query("DELETE FROM user")
    fun wipeTable()
}
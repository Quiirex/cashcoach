package com.tva.cashcoach.appcomponents.model.wallet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet ORDER BY id ASC")
    fun getAll(): LiveData<List<Wallet>>

    @Query("SELECT * FROM wallet WHERE id = :id")
    fun getById(id: Long): Wallet?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(wallet: Wallet)

    @Delete
    fun delete(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Query("DELETE FROM wallet")
    fun wipeTable()
}
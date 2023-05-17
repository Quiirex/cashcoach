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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wallet: Wallet)

    @Delete
    suspend fun delete(wallet: Wallet)

    @Update
    suspend fun update(wallet: Wallet)

    @Query("DELETE FROM wallet")
    suspend fun wipeTable()
}
package com.tva.cashcoach.appcomponents.model.wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "type") val type: String = "",
    @ColumnInfo(name = "balance") val balance: Double = 0.0,
    @ColumnInfo(name = "user_id") val user_id: Long = 0,
)
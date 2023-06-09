package com.tva.cashcoach.infrastructure.model.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "value") val value: Double = 0.0,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "date") val date: Date = Date(),
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "wallet_id") val wallet_id: Int = 0,
    @ColumnInfo(name = "type") var type: String = "",
    @ColumnInfo(name = "currency") val currency: String = ""
)
package com.tva.cashcoach.appcomponents.model.income

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "value") val value: Double = 0.0,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "date") val date: Date = Date(),
    @ColumnInfo(name = "category_id") val category_id: Long = 0,
    @ColumnInfo(name = "wallet_id") val wallet_id: Long = 0,
)
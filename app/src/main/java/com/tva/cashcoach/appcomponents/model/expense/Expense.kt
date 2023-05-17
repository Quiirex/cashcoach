package com.tva.cashcoach.appcomponents.model.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "value") val value: Double = 0.0,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "date") val date: Date = Date(),
    @ColumnInfo(
        name = "attachment",
        typeAffinity = ColumnInfo.BLOB
    ) val attachment: ByteArray? = null
)
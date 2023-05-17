package com.tva.cashcoach.appcomponents.model.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "type") val type: String = "",
)
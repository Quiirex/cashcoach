package com.tva.cashcoach.appcomponents.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "uid") val uid: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "surname") val surname: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "currency") val currency: String = "",
    @ColumnInfo(name = "language") val language: String = "",
    @ColumnInfo(name = "theme") val theme: String = "",
    @ColumnInfo(name = "avatar") val avatar: String = "",
)
package com.example.androimocktest.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "items")
data class Items(
    @PrimaryKey(autoGenerate = true)
    val idItems: Int = 0,
    @ColumnInfo(name = "itemsName")
    var itemsName: String?,
    @ColumnInfo(name = "quantity")
    var qty: String?,
    @ColumnInfo(name = "supplier")
    var supplier: String?,
    @ColumnInfo(name = "date")
    var date: String?
) : Parcelable

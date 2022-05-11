package com.example.androimocktest.data.local.dao

import androidx.room.*
import com.example.androimocktest.data.local.entity.Items
@Dao
interface ItemsDao {
    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Items>

    @Insert
    suspend fun insertItems(items: Items): Long

    @Update
    suspend fun updateItems(items: Items): Int

    @Delete
    suspend fun deleteItems(items: Items): Int

    @Query("SELECT * FROM ITEMS WHERE idItems == :idItems")
    suspend fun showItems(idItems : Int): List<Items>

}
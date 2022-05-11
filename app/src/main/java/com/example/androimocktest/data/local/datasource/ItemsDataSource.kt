package com.example.androimocktest.data.local.datasource

import com.example.androimocktest.data.local.entity.Items

interface ItemsDataSource {
    suspend fun getAllItems(): List<Items>

    suspend fun insertItems(items: Items): Long

    suspend fun deleteItems(items: Items): Int

    suspend fun updateItems(items: Items): Int

    suspend fun showItems(idItems: Int): List<Items>
}
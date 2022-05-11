package com.example.androimocktest.data.local.datasource

import com.example.androimocktest.data.local.dao.ItemsDao
import com.example.androimocktest.data.local.entity.Items

class ItemsDataSourceImpl(private val dao: ItemsDao) : ItemsDataSource {
    override suspend fun getAllItems(): List<Items> {
        return dao.getAllItems()
    }

    override suspend fun insertItems(items: Items): Long {
        return dao.insertItems(items)
    }

    override suspend fun deleteItems(items: Items): Int {
        return dao.deleteItems(items)
    }

    override suspend fun updateItems(items: Items): Int {
        return dao.updateItems(items)
    }

    override suspend fun showItems(idItems: Int): List<Items> {
        return dao.showItems(idItems)
    }
}
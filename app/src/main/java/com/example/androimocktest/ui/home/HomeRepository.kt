package com.example.androimocktest.ui.home

import com.example.androimocktest.data.local.datasource.ItemsDataSource
import com.example.androimocktest.data.local.entity.Items

class HomeRepository(private val itemsDataSource: ItemsDataSource) : HomeContract.Repository{
    override suspend fun getAllItems(): List<Items> {
        return itemsDataSource.getAllItems()
    }
}
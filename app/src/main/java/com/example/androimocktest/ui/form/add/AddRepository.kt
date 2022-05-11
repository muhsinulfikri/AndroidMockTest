package com.example.androimocktest.ui.form.add

import com.example.androimocktest.data.local.datasource.ItemsDataSource
import com.example.androimocktest.data.local.entity.Items

class AddRepository(private val itemDataSource: ItemsDataSource): AddContract.Repository {
    override suspend fun insertItems(items: Items): Long {
        return itemDataSource.insertItems(items)
    }

}
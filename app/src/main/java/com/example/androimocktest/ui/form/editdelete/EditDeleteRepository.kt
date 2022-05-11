package com.example.androimocktest.ui.form.editdelete

import com.example.androimocktest.data.local.datasource.ItemsDataSource
import com.example.androimocktest.data.local.entity.Items

class EditDeleteRepository(private val itemsDataSource: ItemsDataSource): EditDeleteContract.Repository {
    override suspend fun updateItems(items: Items): Int {
        return itemsDataSource.updateItems(items)
    }

    override suspend fun deleteItems(items: Items): Int {
        return itemsDataSource.deleteItems(items)
    }
}
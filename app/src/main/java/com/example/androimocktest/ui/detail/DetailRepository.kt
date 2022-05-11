package com.example.androimocktest.ui.detail

import com.example.androimocktest.data.local.datasource.ItemsDataSource

class DetailRepository(private val localDataSource: ItemsDataSource): DetailContract.Repository {
}
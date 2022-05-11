package com.example.androimocktest.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.androimocktest.base.arch.BaseContract
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items

interface HomeContract {
    interface View: BaseContract.BaseView {
        fun getData()
        fun initList()
        fun setListData(data: List<Items>)
        fun setupRecyclerView()
        fun setupSwipeRefresh()

    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllItems()
        fun getItemsLiveData(): MutableLiveData<Resource<List<Items>>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getAllItems(): List<Items>
    }
}
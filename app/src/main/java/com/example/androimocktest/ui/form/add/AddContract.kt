package com.example.androimocktest.ui.form.add

import androidx.lifecycle.MutableLiveData
import com.example.androimocktest.base.arch.BaseContract
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items

interface AddContract {
    interface View: BaseContract.BaseView {
        fun showToast(msg: String)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun insertItems(items: Items)
        fun getItemsResultLiveData(): MutableLiveData<Resource<Number>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun insertItems(items: Items): Long
    }
}
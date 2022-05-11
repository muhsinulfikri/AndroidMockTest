package com.example.androimocktest.ui.form.editdelete

import androidx.lifecycle.MutableLiveData
import com.example.androimocktest.base.arch.BaseContract
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items

interface EditDeleteContract {

    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun showToast(msg: String)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun updateItems(items: Items)
        fun deleteItems(items: Items)
        fun getItemsResultLiveData(): MutableLiveData<Pair<String, Resource<Number>>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun updateItems(items: Items): Int
        suspend fun deleteItems(items: Items): Int
    }
}
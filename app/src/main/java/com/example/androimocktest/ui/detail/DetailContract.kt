package com.example.androimocktest.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.androimocktest.base.arch.BaseContract
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items

interface DetailContract {
    interface View: BaseContract.BaseView {
        fun getIntentData()
    }

    interface ViewModel: BaseContract.BaseViewModel {
    }

    interface Repository: BaseContract.BaseRepository {
    }
}
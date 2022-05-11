package com.example.androimocktest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androimocktest.base.arch.BaseViewModelImpl
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : BaseViewModelImpl(),
    HomeContract.ViewModel {

    private val data = MutableLiveData<Resource<List<Items>>>()
    override fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                delay(2000)
                val items = repository.getAllItems()
                viewModelScope.launch(Dispatchers.Main){
                    data.value = Resource.Success(items)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main){
                    data.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getItemsLiveData(): MutableLiveData<Resource<List<Items>>> = data

}
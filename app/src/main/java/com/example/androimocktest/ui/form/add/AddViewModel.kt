package com.example.androimocktest.ui.form.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androimocktest.base.arch.BaseViewModelImpl
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel(private val repository: AddRepository): BaseViewModelImpl(), AddContract.ViewModel {

    private val addItemsResultLiveData = MutableLiveData<Resource<Number>>()

    override fun insertItems(items: Items) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val insertedRowId = repository.insertItems(items)
                viewModelScope.launch(Dispatchers.Main){
                    if (insertedRowId > 0){
                        addItemsResultLiveData.value = Resource.Success(insertedRowId)
                    } else {
                        addItemsResultLiveData.value = Resource.Error("", insertedRowId)
                    }
                }
            } catch (error: Exception){
                viewModelScope.launch(Dispatchers.Main){
                    addItemsResultLiveData.value = Resource.Error(error.message.toString())
                }
            }
        }
    }

    override fun getItemsResultLiveData(): MutableLiveData<Resource<Number>> = addItemsResultLiveData
}
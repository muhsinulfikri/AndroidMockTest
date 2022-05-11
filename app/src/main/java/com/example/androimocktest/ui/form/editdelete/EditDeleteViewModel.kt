package com.example.androimocktest.ui.form.editdelete

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androimocktest.base.arch.BaseContract
import com.example.androimocktest.base.arch.BaseViewModelImpl
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.util.ActionConstant.ACTION_DELETE
import com.example.androimocktest.util.ActionConstant.ACTION_UPDATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDeleteViewModel(private val repository: EditDeleteRepository): BaseViewModelImpl(), EditDeleteContract.ViewModel {

    private val editDeleteItemsResultLiveData = MutableLiveData<Pair<String, Resource<Number>>>()

    override fun updateItems(items: Items) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val insertedRowId = repository.updateItems(items)
                viewModelScope.launch(Dispatchers.Main){
                    if (insertedRowId > 0){
                        editDeleteItemsResultLiveData.value = Pair(ACTION_UPDATE, Resource.Success(insertedRowId))
                    } else {
                        editDeleteItemsResultLiveData.value = Pair(ACTION_UPDATE, Resource.Error("", insertedRowId))
                    }
                }
            } catch (error: Exception){
                viewModelScope.launch(Dispatchers.Main){
                    editDeleteItemsResultLiveData.value = Pair(ACTION_UPDATE, Resource.Error(error.message.toString()))
                }
            }
        }
    }

    override fun deleteItems(items: Items) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val insertedRowId = repository.deleteItems(items)
                viewModelScope.launch(Dispatchers.Main){
                    if (insertedRowId > 0){
                        editDeleteItemsResultLiveData.value = Pair(ACTION_DELETE, Resource.Success(insertedRowId))
                    } else {
                        editDeleteItemsResultLiveData.value = Pair(ACTION_DELETE, Resource.Error("", insertedRowId))
                    }
                }
            } catch (error: Exception){
                viewModelScope.launch(Dispatchers.Main){
                    editDeleteItemsResultLiveData.value = Pair(ACTION_UPDATE, Resource.Error(error.message.toString()))
                }
            }
        }
    }

    override fun getItemsResultLiveData(): MutableLiveData<Pair<String, Resource<Number>>> = editDeleteItemsResultLiveData

}
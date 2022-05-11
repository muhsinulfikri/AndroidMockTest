package com.example.androimocktest.ui.detail

import com.example.androimocktest.base.arch.BaseViewModelImpl

class DetailViewModel(private val repository: DetailContract.Repository): DetailContract.ViewModel, BaseViewModelImpl() {
}
package com.example.androimocktest.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity <B : ViewBinding, VM : BaseContract.BaseViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {

    private lateinit var binding: B
    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        viewModel = initViewModel()
        observeData()
        initView()
    }

    abstract fun initView()
    abstract fun initViewModel(): VM

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModel

    override fun observeData() {

    }

    override fun showContent(isVisible: Boolean) {

    }

    override fun showLoading(isVisible: Boolean) {

    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
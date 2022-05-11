package com.example.androimocktest.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androimocktest.base.arch.BaseActivity
import com.example.androimocktest.base.arch.GenericViewModelFactory
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.ItemsDatabase
import com.example.androimocktest.data.local.datasource.ItemsDataSourceImpl
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.databinding.ActivityHomeBinding
import com.example.androimocktest.ui.detail.DetailActivity
import com.example.androimocktest.ui.form.add.AddActivity
import com.example.androimocktest.ui.home.adapter.HomeAdapter

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(ActivityHomeBinding::inflate),
    HomeContract.View {

    private lateinit var adapter: HomeAdapter

    override fun initView() {
        setupRecyclerView()
        setupSwipeRefresh()
        getViewBinding().fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun initViewModel(): HomeViewModel {
        val dataSource = ItemsDataSourceImpl(ItemsDatabase.getInstance(this).itemsDao())
        val repository = HomeRepository(dataSource)
        return GenericViewModelFactory(HomeViewModel(repository)).create(HomeViewModel::class.java)
    }

    override fun getData() {
        getViewModel().getAllItems()
    }

    override fun initList() {
        TODO("Not yet implemented")
    }

    override fun setListData(data: List<Items>) {
        adapter.setItems(data)
    }

    override fun setupRecyclerView() {
        adapter = HomeAdapter { items ->
            DetailActivity.startActivityToDetail(this, items)
        }
        getViewBinding().rvItems.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 1)
            adapter = this@HomeActivity.adapter
        }
    }

    override fun setupSwipeRefresh() {
        getViewBinding().srlItem.setOnRefreshListener {
            getData()
            getViewBinding().srlItem.isRefreshing = false
        }
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().progressBar.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvItems.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
    }

    override fun observeData() {
        getViewModel().getItemsLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    it.data?.let { items ->
                        if (items.isEmpty()) {
                            showError(true, "tambahkan barang")
                            showContent(false)
                        } else {
                            showError(false)
                            showContent(true)
                            setListData(items)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, it.message)
                }
            }
        }
    }
}
package com.example.androimocktest.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.androimocktest.base.arch.BaseActivity
import com.example.androimocktest.base.arch.GenericViewModelFactory
import com.example.androimocktest.data.local.ItemsDatabase
import com.example.androimocktest.data.local.datasource.ItemsDataSourceImpl
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.databinding.ActivityDetailBinding
import com.example.androimocktest.ui.form.editdelete.EditDeleteActivity
import com.example.androimocktest.ui.form.editdelete.EditDeleteViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(ActivityDetailBinding::inflate),
    DetailContract.View {

    private var items: Items? = null

    companion object{
        private const val INTENT_ITEMS_DETAIL = "INTENT_ITEMS_DETAIL"
        @JvmStatic
        fun startActivityToDetail(context: Context?, items: Items? = null){
            val intent = Intent(context, DetailActivity::class.java)
            items?.let {
                intent.putExtra(INTENT_ITEMS_DETAIL, items)
            }
            context?.startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Items"

        getIntentData()
        initItems()
        setClickListener()
    }

    override fun initViewModel(): DetailViewModel {
        val repository = DetailRepository(ItemsDataSourceImpl(ItemsDatabase.getInstance(this).itemsDao()))
        return GenericViewModelFactory(DetailViewModel(repository)).create(DetailViewModel::class.java)
    }

    override fun getIntentData() {
        items = intent.getParcelableExtra(INTENT_ITEMS_DETAIL)
    }

    private fun initItems(){
        items?.let { items ->
            getViewBinding().tvTitleDetail.text = items.itemsName?.uppercase()
            getViewBinding().tvQtyDetail.text = items.qty
            getViewBinding().tvSupplierDetail.text = items.supplier
            getViewBinding().tvDateDetail.text = items.date
        }
    }

    private fun setClickListener(){
        getViewBinding().btnUpdateDetail.setOnClickListener {
            EditDeleteActivity.startActivityToEditDelete(this, items)
        }
    }
}
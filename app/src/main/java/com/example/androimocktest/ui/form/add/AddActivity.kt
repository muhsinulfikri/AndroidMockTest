package com.example.androimocktest.ui.form.add

import android.app.DatePickerDialog
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.androimocktest.base.arch.BaseActivity
import com.example.androimocktest.base.arch.GenericViewModelFactory
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.ItemsDatabase
import com.example.androimocktest.data.local.datasource.ItemsDataSourceImpl
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>(ActivityAddBinding::inflate),
    AddContract.View {

    private var items: Items? = null
    private val cal = Calendar.getInstance()
    val dateCal = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
    }

    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Items"
        setClickListener()
    }

    private fun setClickListener() {
        getViewBinding().btnSaveItem.setOnClickListener {
            addItems()
        }
        getViewBinding().etInputDate.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(view: View?) {
                    DatePickerDialog(
                        this@AddActivity,
                        dateCal,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        )
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getItemsResultLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    showToast("Success Add Items")
                }
                else -> {
                    showToast("Failed Add Items")
                }
            }
            this.finish()
        }
    }

    private fun addItems() {
        if (validateForm()) {
            items = Items(
                itemsName = getViewBinding().etInputName.text.toString(),
                qty = getViewBinding().etInputQty.text.toString(),
                supplier = getViewBinding().etInputSupplier.text.toString(),
                date = getViewBinding().etInputDate.text.toString()
            )
            items?.let { getViewModel().insertItems(it) }
        }
    }

    private fun validateForm(): Boolean {
        val itemsName = getViewBinding().etInputName.text.toString()
        val itemsQty = getViewBinding().etInputQty.text.toString()
        val supplier = getViewBinding().etInputSupplier.text.toString()
        val date = getViewBinding().etInputDate.text.toString()
        val isFormValid: Boolean

        when {
            itemsName.isEmpty() -> {
                isFormValid = false
                getViewBinding().etInputName.error = "Please Enter Name"
            }
            itemsQty.isEmpty() -> {
                isFormValid = false
                getViewBinding().etInputQty.error = "Please Enter Quantity"
            }
            supplier.isEmpty() -> {
                isFormValid = false
                getViewBinding().etInputSupplier.error = "Please Enter Supplier"
            }
            date.isEmpty() -> {
                isFormValid = false
                getViewBinding().etInputDate.error = "Please Enter Date"
            }
            else -> {
                isFormValid = true
            }
        }
        return isFormValid
    }

    private fun updateDateInView() {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        getViewBinding().etInputDate.setText(sdf.format(cal.time))
    }

    override fun initViewModel(): AddViewModel {
        val repository = AddRepository(
            ItemsDataSourceImpl(ItemsDatabase.getInstance(this).itemsDao()),
        )
        return GenericViewModelFactory(AddViewModel(repository)).create(
            AddViewModel::class.java
        )
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
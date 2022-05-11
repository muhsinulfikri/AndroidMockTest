package com.example.androimocktest.ui.form.editdelete

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.androimocktest.base.arch.BaseActivity
import com.example.androimocktest.base.arch.GenericViewModelFactory
import com.example.androimocktest.base.model.Resource
import com.example.androimocktest.data.local.ItemsDatabase
import com.example.androimocktest.data.local.datasource.ItemsDataSourceImpl
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.databinding.ActivityEditDeleteBinding
import com.example.androimocktest.ui.home.HomeActivity
import com.example.androimocktest.util.ActionConstant.ACTION_DELETE
import com.example.androimocktest.util.ActionConstant.ACTION_UPDATE
import java.text.SimpleDateFormat
import java.util.*

class EditDeleteActivity : BaseActivity<ActivityEditDeleteBinding,
        EditDeleteViewModel>(ActivityEditDeleteBinding::inflate),
    EditDeleteContract.View {

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

    companion object{
        private const val INTENT_ITEM_EDIT_DELETE = "INTENT_ITEM_EDIT_DELETE"

        @JvmStatic
        fun startActivityToEditDelete(context: Context?, items: Items? = null){
            val intent = Intent(context, EditDeleteActivity::class.java)
            intent.putExtra(INTENT_ITEM_EDIT_DELETE, items)
            context?.startActivity(intent)
        }
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getItemsResultLiveData().observe(this){
            when(it.first){
                ACTION_UPDATE -> {
                    if (it.second is Resource.Success){
                        showToast("Update Success")
                    } else {
                        showToast("Update Failed")
                    }
                }
                ACTION_DELETE -> {
                    if (it.second is Resource.Success){
                        showToast("Delete Success")
                    } else {
                        showToast("Delete Failed")
                    }
                }
            }
            this.finish()
        }
    }

    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update or Delete Items"

        getIntentData()
        initItems()
        setClickListener()
    }

    private fun setClickListener(){
        getViewBinding().btnUpdate.setOnClickListener {
            updateItems()
        }
        getViewBinding().btnDelete.setOnClickListener {
            items?.let {
                getViewModel().deleteItems(it)
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        getViewBinding().etInputDate.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(view: View?) {
                    DatePickerDialog(
                        this@EditDeleteActivity,
                        dateCal,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        )
    }

    override fun initViewModel(): EditDeleteViewModel {
        val repository = EditDeleteRepository(ItemsDataSourceImpl(ItemsDatabase.getInstance(this).itemsDao()))
        return GenericViewModelFactory(EditDeleteViewModel(repository)).create(EditDeleteViewModel::class.java)
    }

    override fun getIntentData() {
        items = intent.getParcelableExtra(INTENT_ITEM_EDIT_DELETE)
    }

    private fun validateForm(): Boolean {
        val itemsName = getViewBinding().etInputName.text.toString()
        val itemsQty = getViewBinding().etInputQty.text.toString()
        val supplier = getViewBinding().etInputSupplier.text.toString()
        val date = getViewBinding().etInputDate.text.toString()
        val isFormValid: Boolean

        when {
            itemsName.isEmpty() ->{
                isFormValid = false
                getViewBinding().etInputName.error = "Please Enter Name"
            }
            itemsQty.isEmpty() ->{
                isFormValid = false
                getViewBinding().etInputQty.error = "Please Enter Quantity"
            }
            supplier.isEmpty() ->{
                isFormValid = false
                getViewBinding().etInputSupplier.error = "Please Enter Supplier"
            }
            date.isEmpty() ->{
                isFormValid = false
                getViewBinding().etInputDate.error = "Please Enter Date"
            }
            else -> {
                isFormValid = true
            }
        }
        return isFormValid
    }

    private fun updateItems(){
        if (validateForm()){
            items = items?.copy()?.apply {
                itemsName = getViewBinding().etInputName.text.toString()
                qty = getViewBinding().etInputQty.text.toString()
                supplier = getViewBinding().etInputSupplier.text.toString()
                date = getViewBinding().etInputDate.text.toString()
            }
            items?.let { getViewModel().updateItems(it) }
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun initItems(){
        items?.let {
            getViewBinding().etInputName.setText(it.itemsName)
            getViewBinding().etInputQty.setText(it.qty)
            getViewBinding().etInputSupplier.setText(it.supplier)
            getViewBinding().etInputDate.setText(it.date)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) showAlertDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun updateDateInView() {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        getViewBinding().etInputDate.setText(sdf.format(cal.time))
    }

    private fun showAlertDialog() {
        val dialogTitle = "Cancel"
        val dialogMessage = "Are you sure to cancel without update all changes in this form? All changes you\\'ve made will be delete"
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Cancel") { _, _ ->
                finish()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.cancel() }
        }
        alertDialogBuilder.create().show()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
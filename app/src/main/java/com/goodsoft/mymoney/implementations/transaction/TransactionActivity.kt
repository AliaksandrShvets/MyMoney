package com.goodsoft.mymoney.implementations.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.TransactionFragmentBinding
import com.goodsoft.mymoney.core.Args
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.core.generateDatePickerDialog
import com.goodsoft.mymoney.enums.TransactionType
import me.tatarka.bindingcollectionadapter2.BR


class TransactionActivity : AppCompatActivity() {

    private val viewModel by viewModels<TransactionViewModel>()
    private lateinit var binding: TransactionFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.item, R.layout.item_category_checkable, {
            viewModel.checkItem(it)
        })
        binding.close.setOnClickListener { finish() }
        binding.transactionDate.setOnClickListener { showDatePickerDialog() }
        initViewModel()
    }

    private fun initViewModel() {
        intent.getStringExtra(Args.TRANSACTION_TYPE)?.let {
            viewModel.transactionType.set(TransactionType.valueOf(it))
        }
        viewModel.createTransactionResult.observe(this, Observer {
            finish()
        })
    }

    private fun showDatePickerDialog() {
        generateDatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDateCreateTransaction(year, month, day)
        }, viewModel.dateCreateTransaction.get()).show()
    }
}

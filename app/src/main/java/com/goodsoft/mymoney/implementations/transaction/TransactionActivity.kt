package com.goodsoft.mymoney.implementations.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.TransactionFragmentBinding
import com.goodsoft.mymoney.core.Args
import com.goodsoft.mymoney.enums.TransactionType
import com.goodsoft.mymoney.core.generateDatePickerDialog
import java.util.*


class TransactionActivity : AppCompatActivity() {

    private val viewModel by viewModels<TransactionViewModel>()
    private lateinit var binding: TransactionFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        binding.transactionDate.setOnClickListener { showDatePickerDialog() }
        binding.close.setOnClickListener { finish() }
        binding.viewModel = viewModel
        initViewModel()
    }

    private fun initViewModel() {
        intent.getStringExtra(Args.TRANSACTION_TYPE)?.let {
            viewModel.transactionType.set(TransactionType.valueOf(it))
        }
    }

    private fun showDatePickerDialog() {
        generateDatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDateCreateTransaction(Calendar.getInstance().apply {
                set(year, month, day)
            })
        }).show()
    }

}

package com.goodsoft.mymoney.implementations.main.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.Args
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.databinding.FragmentMainBinding
import com.goodsoft.mymoney.enums.TransactionType
import com.goodsoft.mymoney.implementations.transaction.TransactionActivity
import me.tatarka.bindingcollectionadapter2.BR


class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.item, R.layout.item_transaction)
        binding.addIncome.setOnClickListener { startTransactionActivity(TransactionType.INCOME) }
        binding.addOutcome.setOnClickListener { startTransactionActivity(TransactionType.OUTCOME) }
        return binding.root
    }

    private fun startTransactionActivity(transactionType: TransactionType) {
        startActivity(Intent(context, TransactionActivity::class.java).apply {
            putExtra(Args.TRANSACTION_TYPE, transactionType.name)
        })
    }

}
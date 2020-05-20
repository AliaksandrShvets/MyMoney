package com.goodsoft.mymoney.implementations.backdrop.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.goodsoft.mymoney.MainFragmentBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.enums.TransactionType
import com.goodsoft.mymoney.implementations.transaction.TransactionFragmentArgs
import me.tatarka.bindingcollectionadapter2.BR


class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.item, R.layout.item_transaction)
        binding.addIncome.setOnClickListener { startTransactionActivity(TransactionType.INCOME) }
        binding.addOutcome.setOnClickListener { startTransactionActivity(TransactionType.OUTCOME) }
        return binding.root
    }

    private fun startTransactionActivity(transactionType: TransactionType) {
        val bundle = TransactionFragmentArgs(transactionType).toBundle()
        activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.transactionFragment, bundle)
    }
}
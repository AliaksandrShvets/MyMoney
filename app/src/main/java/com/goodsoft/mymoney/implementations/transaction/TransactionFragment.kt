package com.goodsoft.mymoney.implementations.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.TransactionFragmentBinding
import com.goodsoft.mymoney.core.createItemBinding
import com.goodsoft.mymoney.core.generateDatePickerDialog
import me.tatarka.bindingcollectionadapter2.BR


class TransactionFragment : Fragment() {

    private val viewModel by viewModels<TransactionViewModel>()
    private lateinit var binding: TransactionFragmentBinding
    private val args: TransactionFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = createItemBinding(BR.item, R.layout.item_category_checkable, {
            viewModel.checkItem(it)
        })
        binding.transactionDate.setOnClickListener { showDatePickerDialog() }
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        viewModel.finishEvent.observe(viewLifecycleOwner, Observer { findNavController().popBackStack() })
        viewModel.categoryError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, R.string.activity_transaction_category_error, Toast.LENGTH_SHORT).show()
        })
        viewModel.initStrings(
                args.transactionType,
                getString(R.string.activity_transaction_amount_error)
        )
    }

    private fun showDatePickerDialog() {
        generateDatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDateCreateTransaction(year, month, day)
        }, viewModel.date.get()).show()
    }
}
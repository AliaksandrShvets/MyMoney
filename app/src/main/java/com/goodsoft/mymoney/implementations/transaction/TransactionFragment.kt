package com.goodsoft.mymoney.implementations.transaction

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
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
        initViewModel(args.transactionEntity)
        return binding.root
    }

    private fun initViewModel(transactionEntity : TransactionEntity?) {
        viewModel.finishEvent.observe(viewLifecycleOwner, Observer {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
            binding.recycler.postDelayed({
                findNavController().popBackStack()
            }, 100)
        })
        viewModel.categoryError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, R.string.activity_transaction_category_error, Toast.LENGTH_SHORT).show()
        })
        viewModel.init(
                args.transactionType,
                getString(R.string.activity_transaction_amount_error),
                transactionEntity
        )
    }

    private fun showDatePickerDialog() {
        generateDatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDateCreateTransaction(year, month, day)
        }, viewModel.date.get()).show()
    }
}
package com.goodsoft.mymoney.implementations.backdrop.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.goodsoft.mymoney.MainFragmentBinding
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.core.OnItemClickListener
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsRoomRepository
import com.goodsoft.mymoney.enums.TransactionType
import com.goodsoft.mymoney.implementations.transaction.TransactionFragmentArgs
import com.goodsoft.mymoney.widgets.funds.FoundsData
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass


class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.itemBinding = OnItemBindClass<Any>()
                .map(FoundsData::class.java, BR.data, R.layout.funds_item)
                .map(TransactionEntity::class.java) { itemBinding, _, _ ->
                    itemBinding.set(BR.item, R.layout.item_transaction)
                    itemBinding.bindExtra(BR.listener, object : OnItemClickListener<TransactionEntity> {
                        override fun onClick(item: TransactionEntity) {
                        }

                        override fun onMenuClick(view: View, item: TransactionEntity) {
                            showMenu(view, item)
                        }
                    })
                }
                .map(TransactionHeaderItem::class.java, BR.item, R.layout.item_transaction_header)
        binding.addIncome.setOnClickListener { startTransactionActivity(TransactionType.INCOME) }
        binding.addOutcome.setOnClickListener { startTransactionActivity(TransactionType.OUTCOME) }
        return binding.root
    }

    private fun showMenu(view: View, transactionEntity: TransactionEntity) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_category_item)
        popup.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.edit -> {
                    startTransactionActivity(transactionEntity.type, transactionEntity)
                    true
                }
                R.id.delete -> {
                    TransactionsRoomRepository().delete(transactionEntity).subscribe({}, {})
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun startTransactionActivity(transactionType: TransactionType, transactionEntity: TransactionEntity? = null) {
        val bundle = TransactionFragmentArgs(transactionType, transactionEntity).toBundle()
        activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.transactionFragment, bundle)
    }
}
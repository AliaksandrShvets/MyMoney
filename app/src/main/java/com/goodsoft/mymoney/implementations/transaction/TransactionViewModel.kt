package com.goodsoft.mymoney.implementations.transaction

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.core.SingleLiveEvent
import com.goodsoft.mymoney.core.toCategoryItem
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsRoomRepository
import com.goodsoft.mymoney.enums.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.*


class TransactionViewModel : ViewModel() {

    val createTransactionResult = SingleLiveEvent<Unit>()
    val transactionType = ObservableField<TransactionType>()
    val dateCreateTransaction = ObservableField(Date())
    val sumTransaction = ObservableField<String>()
    val items = DiffObservableList(object : DiffUtil.ItemCallback<CheckableCategoryItem>() {
        override fun areItemsTheSame(oldItem: CheckableCategoryItem, newItem: CheckableCategoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckableCategoryItem, newItem: CheckableCategoryItem): Boolean {
            return oldItem.category == newItem.category &&
                    oldItem.isSelected.value == newItem.isSelected.value
        }
    })

    init {
        initCategories()
    }

    fun setDateCreateTransaction(year: Int, month: Int, dayOfMonth: Int) {
        dateCreateTransaction.set(Calendar.getInstance().apply { set(year, month, dayOfMonth) }.time)
    }

    fun checkItem(item: CheckableCategoryItem) {
        items.forEach { it.isSelected.value = false }
        items.find { item.category == it.category }?.isSelected?.value = true
    }

    fun createTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val date = dateCreateTransaction.get() ?: Date()
            val type = transactionType.get() ?: TransactionType.INCOME
            val category = items.find { it.isSelected.value == true }?.category?.categoryEntity ?: CategoryEntity("","","")
            val amount = sumTransaction.get()?.toDoubleOrNull() ?: 0.0
            val info = ""
            val transaction = TransactionEntity(date, type, category, amount, info)
            TransactionsRoomRepository().insert(transaction).subscribe({
                createTransactionResult.call()
            }, {})
        }
    }

    @SuppressLint("CheckResult")
    private fun initCategories() {
        CategoriesRoomRepository().getCategories()
                .subscribe({ categoryList ->
                    items.update(categoryList.map {
                        CheckableCategoryItem(it.toCategoryItem(), MutableLiveData(false))
                    })
                }, {})
    }
}
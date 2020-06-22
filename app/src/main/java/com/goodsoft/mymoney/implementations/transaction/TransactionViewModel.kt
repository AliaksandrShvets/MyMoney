package com.goodsoft.mymoney.implementations.transaction

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodsoft.mymoney.core.SingleLiveEvent
import com.goodsoft.mymoney.core.toCategoryItem
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsRoomRepository
import com.goodsoft.mymoney.enums.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.*


class TransactionViewModel : ViewModel() {

    val finishEvent = SingleLiveEvent<Unit>()
    val transactionType = MutableLiveData<TransactionType>()

    val date = ObservableField(Date())
    val amount = ObservableField<String>()
    val amountError = ObservableField<String>()
    val categories = DiffObservableList(CheckableCategoryItem.getDiffItemCallback())
    val categoryError = SingleLiveEvent<Unit>()
    val transaction = ObservableField<TransactionEntity>()

    private var amountErrorString = ""

    init {
        initCategories()
    }

    fun init(
            transactionType: TransactionType,
            amountError: String,
            transactionEntity: TransactionEntity? = null
    ) {
        this.transactionType.value = transactionType
        amountErrorString = amountError
        transactionEntity?.let {
            date.set(it.date)
            amount.set(it.amount.toString())
            transaction.set(it)
        }
    }

    fun finish() {
        finishEvent.call()
    }

    fun setDateCreateTransaction(year: Int, month: Int, dayOfMonth: Int) {
        date.set(Calendar.getInstance().apply { set(year, month, dayOfMonth) }.time)
    }

    fun checkItem(item: CheckableCategoryItem) {
        categories.forEach { it.isSelected.value = false }
        categories.find { item.category == it.category }?.isSelected?.value = true
    }

    fun createTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val date = date.get() ?: Date()
            val type = transactionType.value ?: TransactionType.INCOME
            val amount = amount.get()?.toDoubleOrNull()
            if (amount == null) {
                amountError.set(amountErrorString)
                return@launch
            }
            val category = categories.find { it.isSelected.value == true }?.category?.categoryEntity
            if (category == null) {
                withContext(Dispatchers.Main) { categoryError.call() }
                return@launch
            }
            val info = ""
            transaction.get()?.let {
                TransactionsRoomRepository().update(it.apply {
                    this.date = date
                    this.type = type
                    this.category = category
                    this.amount = amount
                    this.info = info
                }).subscribe({ finish() }, {})
            } ?: run {
                val transaction = TransactionEntity(date, type, category, amount, info)
                TransactionsRoomRepository().insert(transaction).subscribe({ finish() }, {})
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun initCategories() {
        CategoriesRoomRepository().getCategories()
                .subscribe({ categoryList ->
                    categories.update(categoryList.map {
                        CheckableCategoryItem(it.toCategoryItem(), MutableLiveData(false))
                    })
                    categories.find { transaction.get()?.category?.id == it.category.categoryEntity.id }?.isSelected?.value = true
                }, {})
    }
}
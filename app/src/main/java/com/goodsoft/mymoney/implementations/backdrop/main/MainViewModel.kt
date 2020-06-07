package com.goodsoft.mymoney.implementations.backdrop.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class MainViewModel : ViewModel() {

    val transactionsItems = DiffObservableList(object : DiffUtil.ItemCallback<TransactionEntity>() {

        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem.date == newItem.date &&
                    oldItem.type == newItem.type &&
                    oldItem.category == newItem.category &&
                    oldItem.amount == newItem.amount &&
                    oldItem.info == newItem.info
        }
    })

    init {
        initTransactions()
    }

    @SuppressLint("CheckResult")
    private fun initTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            TransactionsRoomRepository().getAll().collect {
                withContext(Dispatchers.Main){
                    transactionsItems.update(it)
                }
            }
        }
    }

    /** todo popular categories in progress

    init {
        initCategories(getFavoriteCategories())
    }

    @SuppressLint("CheckResult")
    private fun initCategories(categoriesId: List<String>) {
        CategoriesRoomRepository().getCategoriesByIds(categoriesId)
                .subscribe({ categoryList ->
                    categoriesId.forEach { categoryId ->
                        categoryList.find {categoryId == it.id }
                                ?.let { transactionItems.add(it.toCategoryItem()) }
                    }
                }, {})
    }

    private fun getFavoriteCategories(): List<String> {
        val categoryList = App.sharedPreferences
                .getString(Constants.KEY_FAVORITE_CATEGORIES, null)
                ?.split(',') ?: listOf()
        return categoryList
                .groupingBy { it }
                .eachCount()
                .map { it.value to it.key }
                .sortedByDescending { it.first }
                .map { it.second }
    }*/
}
package com.goodsoft.mymoney.implementations.backdrop.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsRoomRepository
import com.goodsoft.mymoney.enums.TransactionType
import com.goodsoft.mymoney.util.asDate
import com.goodsoft.mymoney.util.asFullDate
import com.goodsoft.mymoney.widgets.funds.Currency
import com.goodsoft.mymoney.widgets.funds.DayAmountSpent
import com.goodsoft.mymoney.widgets.funds.FoundsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.*


class MainViewModel : ViewModel() {

    val items = DiffObservableList(object : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Any, newItem: Any) = true
    })

    val isHolderVisible = MutableLiveData(false)

    init {
        initTransactions()
    }

    @SuppressLint("CheckResult")
    private fun initTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            TransactionsRoomRepository().getAll().collect { transactions ->
                withContext(Dispatchers.Main) {
                    isHolderVisible.value = transactions.isNullOrEmpty()
                    items.update(mutableListOf<Any>().apply {
                        add(getFundsData(transactions))
                        transactions.sortedByDescending { it.date }.groupBy { asFullDate(it.date) }.forEach { transactionsPerDay ->
                            add(TransactionHeaderItem(transactionsPerDay.key,
                                    transactionsPerDay.value.filter { it.type == TransactionType.INCOME }.sumByDouble { it.amount },
                                    transactionsPerDay.value.filter { it.type == TransactionType.OUTCOME }.sumByDouble { it.amount }
                            ))
                            addAll(transactionsPerDay.value)
                        }
                    })
                }
            }
        }
    }

    private fun getFundsData(transactions: List<TransactionEntity>): FoundsData {
        return FoundsData(
                transactions.filter {
                    it.date.after(Date(Date().time - 86400000 * 6)) &&
                            it.date.before(Date()) &&
                            it.type == TransactionType.INCOME
                }.sumByDouble { it.amount },
                transactions.filter {
                    it.date.after(Date(Date().time - 86400000 * 6)) &&
                            it.date.before(Date()) &&
                            it.type == TransactionType.OUTCOME
                }.sumByDouble { it.amount }, Currency.BYN,
                listOf(6, 5, 4, 3, 2, 1, 0).map { day ->
                    val date = Date(Date().time - 86400000 * day)
                    DayAmountSpent(date.time, transactions.filter {
                        asDate(it.date) == asDate(date) &&
                                it.type == TransactionType.OUTCOME
                    }.sumByDouble { it.amount }, Currency.BYN)
                }
        )
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
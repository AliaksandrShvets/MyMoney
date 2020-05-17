package com.goodsoft.mymoney.implementations.main.accounts

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.core.toAccountItem
import com.goodsoft.mymoney.database.tables.accounts.AccountsRoomRepository
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class AccountsViewModel : ViewModel() {

    val items: DiffObservableList<AccountItem> = DiffObservableList(object : DiffUtil.ItemCallback<AccountItem>() {
        override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.currency == newItem.currency
                    && oldItem.icon == newItem.icon
        }
    })

    init {
        getAccounts()
    }

    @SuppressLint("CheckResult")
    fun getAccounts() {
        AccountsRoomRepository().getAccounts()
                .subscribe({ accountList ->
                    val newList = accountList.map { it.toAccountItem() }
                    items.update(newList, items.calculateDiff(newList))
                }, {})
    }

}
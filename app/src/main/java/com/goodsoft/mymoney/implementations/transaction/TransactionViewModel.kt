package com.goodsoft.mymoney.implementations.transaction

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.goodsoft.mymoney.enums.TransactionType
import java.util.*


class TransactionViewModel : ViewModel() {

    val sumTransaction = ObservableDouble(0.0)
    val transactionType = ObservableField<TransactionType>()
    val dateCreateTransaction = ObservableField(Calendar.getInstance())

    fun setDateCreateTransaction(calendar : Calendar) {
        dateCreateTransaction.set(calendar)
    }
}
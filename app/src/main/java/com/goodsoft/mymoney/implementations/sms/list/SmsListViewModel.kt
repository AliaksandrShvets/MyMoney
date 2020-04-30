package com.goodsoft.mymoney.implementations.sms.list

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.goodsoft.mymoney.implementations.sms.parser.SmsEntity


class SmsListViewModel : ViewModel() {

    val items: ObservableArrayList<SmsEntity> = ObservableArrayList()

    fun setSmsList(smsList: Sequence<SmsEntity>) {
        items.addAll(smsList)
    }

}
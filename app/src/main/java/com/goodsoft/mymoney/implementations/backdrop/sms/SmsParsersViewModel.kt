package com.goodsoft.mymoney.implementations.backdrop.sms

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.database.tables.parsers.SmsParsersRoomRepository
import com.goodsoft.mymoney.database.tables.parsers.SmsParserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class SmsParsersViewModel : ViewModel() {

    val smsParserItems: DiffObservableList<SmsParserEntity> = DiffObservableList(object : DiffUtil.ItemCallback<SmsParserEntity>(){
        override fun areItemsTheSame(oldItem: SmsParserEntity, newItem: SmsParserEntity) = oldItem == newItem
        override fun areContentsTheSame(oldItem: SmsParserEntity, newItem: SmsParserEntity) =
                oldItem.address == newItem.address && oldItem.body == newItem.body
    })

    init {
        initSmsParsers()
    }

    @SuppressLint("CheckResult")
    private fun initSmsParsers() {
        viewModelScope.launch(Dispatchers.IO) {
            SmsParsersRoomRepository().getAll().collect { smsParserList ->
                withContext(Dispatchers.Main) {
                    smsParserItems.update(smsParserList)
                }
            }
        }
    }

}
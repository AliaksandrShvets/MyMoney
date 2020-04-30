package com.goodsoft.mymoney.implementations.sms.parser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SmsEntity(
        val address: String,
        val body: String
) : Parcelable {
    fun getString() = address + "\n" + body
}
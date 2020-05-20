package com.goodsoft.mymoney.implementations.backdrop.accounts

import androidx.annotation.DrawableRes


data class AccountItem(
        val id: String,
        val name: String,
        val currency: String,
        @DrawableRes val icon: Int
)
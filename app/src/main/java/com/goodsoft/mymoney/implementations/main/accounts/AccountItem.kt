package com.goodsoft.mymoney.implementations.main.accounts

import androidx.annotation.DrawableRes


data class AccountItem(
        val id: String,
        val name: String,
        val currency: String,
        @DrawableRes val icon: Int
)
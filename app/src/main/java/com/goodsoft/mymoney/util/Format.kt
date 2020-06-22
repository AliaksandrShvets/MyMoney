package com.goodsoft.mymoney.util

import android.content.Context
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.widgets.funds.Currency


fun Context.formatAmount(amount: Double, currency: Currency = Currency.BYN): String {
    val formatResId = when (currency) {
        Currency.EUR -> R.string.format_currency_eur__1
        Currency.RUB -> R.string.format_currency_rub__1
        Currency.USD -> R.string.format_currency_usd__1
        Currency.BYN -> R.string.format_currency_byn__1
        else -> R.string.format_currency_unknown__1
    }

    return String.format(getString(formatResId), amount)
}
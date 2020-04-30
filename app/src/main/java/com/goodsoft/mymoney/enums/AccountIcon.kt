package com.goodsoft.mymoney.enums

import androidx.annotation.DrawableRes
import com.goodsoft.mymoney.R


enum class AccountIcon(@DrawableRes val iconRes: Int) {
    POUND(R.drawable.ic_account_pound),
    AMERICAN_EXPRESS(R.drawable.ic_account_american_express),
    ASIA(R.drawable.ic_account_asia),
    DINNERS(R.drawable.ic_account_diners),
    DISCOVER(R.drawable.ic_account_discover),
    EBAY(R.drawable.ic_account_ebay),
    MAESTRO(R.drawable.ic_account_maestro),
    MASTERCARD(R.drawable.ic_account_mastercard),
    PAYPAL(R.drawable.ic_account_paypal),
    VISA(R.drawable.ic_account_visa),
    WEBMONEY(R.drawable.ic_account_webmoney),
    YANDEX(R.drawable.ic_account_yandex);
}
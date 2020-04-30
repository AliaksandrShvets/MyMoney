package com.goodsoft.mymoney.enums

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.goodsoft.mymoney.R

enum class SmsParameterEnum(val code: Int, @ColorRes val spanColor: Int, @StringRes val stringRes: Int) {
    AMOUNT(1, R.color.sms_parser_element_5_span, R.string.fragment_sms_parser_creation_amount_hint),
    DATE(2, R.color.sms_parser_element_1_span, R.string.fragment_sms_parser_creation_date_hint),
    GROUP(3, R.color.sms_parser_element_4_span, R.string.fragment_sms_parser_creation_group_hint);

    companion object {
        fun getElementByCode(code: Int) = values().find { it.code == code }
    }
}
package com.goodsoft.mymoney.implementations.sms.parser

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class SmsParserCreationViewModel : ViewModel() {
    val spannableSms = ObservableField<CharSequence>()
    val sms = ObservableField<SmsEntity>(
            SmsEntity(
                    "Test.Kt",
                    "Big text for test. Big text for test." +
                            "Big text for test. Big text for test." +
                            "Big text for test. Big text for test." +
                            "Big text for test. Big text for test." +
                            "Big text for test."
            )
    )

    val amount = ObservableField<String>()
    val date = ObservableField<String>()
    val group = ObservableField<String>()
}
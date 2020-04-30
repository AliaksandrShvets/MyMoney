package com.goodsoft.mymoney.core

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun generateDatePickerDialog(
        context: Context,
        listener: DatePickerDialog.OnDateSetListener
) : DatePickerDialog {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
            context,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
    )
}
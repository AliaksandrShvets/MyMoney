package com.goodsoft.mymoney.util

import java.text.SimpleDateFormat
import java.util.*


val timeFormat: SimpleDateFormat
    get() = SimpleDateFormat("HH:mm", Locale.getDefault())

val fullTimeFormat: SimpleDateFormat
    get() = SimpleDateFormat("HH:mm", Locale.getDefault())

val dateFormat: SimpleDateFormat
    get() = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

val fullDateFormat: SimpleDateFormat
    get() = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

val dateWithTimeFormat: SimpleDateFormat
    get() = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())

private fun Date?.asFormat(dateFormat : SimpleDateFormat) : String {
    return this?.let { dateFormat.format(this) } ?: ""
}

fun asTime(date: Date?) = date.asFormat(timeFormat)

fun asFullTime(date: Date?) = date.asFormat(fullTimeFormat)

fun asDate(date: Date?) = date.asFormat(dateFormat)

fun asFullDate(date: Date?) = date.asFormat(fullDateFormat)

fun asDateWithTime(date: Date?) = date.asFormat(dateWithTimeFormat)
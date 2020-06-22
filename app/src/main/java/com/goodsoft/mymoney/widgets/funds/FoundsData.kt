package com.goodsoft.mymoney.widgets.funds


data class FoundsData(
        val totalAmount: Double,
        val currency: Currency,
        val daysAmountSpentList: List<DayAmountSpent>
)
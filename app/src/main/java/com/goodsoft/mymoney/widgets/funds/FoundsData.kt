package com.goodsoft.mymoney.widgets.funds


data class FoundsData(
        val totalIncome: Double,
        val totalOutcome: Double,
        val currency: Currency,
        val daysAmountSpentList: List<DayAmountSpent>
)
package com.goodsoft.mymoney.implementations.sms.parser


data class ExpressionEntity(
        val start: Int,
        val end: Int,
        val regex: String,
        val delimiterIndex: Int
)
package com.goodsoft.mymoney.database.tables.parsers

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "SmsParserEntity")
data class SmsParserEntity(
        @PrimaryKey
        val id: String = UUID.randomUUID().toString(),
        val address: String,
        val body: String,
        val parts: String
)
package com.goodsoft.mymoney.database.tables.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "AccountEntity")
data class AccountEntity(
        @PrimaryKey
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val currencyCode: String,
        val icon: String
)
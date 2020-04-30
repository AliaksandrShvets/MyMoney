package com.goodsoft.mymoney.database.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "CategoryEntity")
data class CategoryEntity(
        @PrimaryKey
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val icon: String
)
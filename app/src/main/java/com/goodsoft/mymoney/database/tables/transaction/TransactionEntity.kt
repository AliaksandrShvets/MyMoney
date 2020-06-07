package com.goodsoft.mymoney.database.tables.transaction

import androidx.annotation.DrawableRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.enums.CategoryIcon
import com.goodsoft.mymoney.enums.TransactionType
import java.util.*


@Entity(tableName = "TransactionEntity")
data class TransactionEntity(
        val date: Date,
        val type: TransactionType,
        val category: CategoryEntity,
        val amount: Double,
        val info: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @DrawableRes
    fun getIcon():  Int {
        return CategoryIcon.valueOf(category.icon).iconRes
    }
}
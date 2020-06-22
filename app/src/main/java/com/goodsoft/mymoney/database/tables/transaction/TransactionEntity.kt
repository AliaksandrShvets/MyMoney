package com.goodsoft.mymoney.database.tables.transaction

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.enums.CategoryIcon
import com.goodsoft.mymoney.enums.TransactionType
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "TransactionEntity")
data class TransactionEntity(
        var date: Date,
        var type: TransactionType,
        var category: CategoryEntity,
        var amount: Double,
        var info: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @DrawableRes
    fun getIcon():  Int {
        return CategoryIcon.valueOf(category.icon).iconRes
    }
}
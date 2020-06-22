package com.goodsoft.mymoney.database.tables.categories

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "CategoryEntity")
data class CategoryEntity(
        @PrimaryKey
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val icon: String
) : Parcelable
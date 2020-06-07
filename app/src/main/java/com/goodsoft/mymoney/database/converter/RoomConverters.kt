package com.goodsoft.mymoney.database.converter

import androidx.room.TypeConverter
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.enums.TransactionType
import com.google.gson.Gson
import java.util.*


class RoomConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun fromTransactionType(date: TransactionType?): String? {
        return date?.name
    }

    @TypeConverter
    fun toTransactionType(value: String?): TransactionType? {
        return if (value == null) null else TransactionType.valueOf(value)
    }

    @TypeConverter
    fun fromCategory(date: CategoryEntity?): String? {
        return Gson().toJson(date)
    }

    @TypeConverter
    fun toCategory(value: String?): CategoryEntity? {
        return Gson().fromJson(value, CategoryEntity::class.java)
    }
}
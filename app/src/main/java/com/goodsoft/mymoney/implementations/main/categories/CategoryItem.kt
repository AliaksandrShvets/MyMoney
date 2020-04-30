package com.goodsoft.mymoney.implementations.main.categories

import androidx.annotation.DrawableRes
import com.goodsoft.mymoney.database.categories.CategoryEntity


data class CategoryItem(
        val categoryEntity: CategoryEntity,
        @DrawableRes val icon: Int
)
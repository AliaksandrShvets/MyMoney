package com.goodsoft.mymoney.implementations.main.categories.editor

import com.goodsoft.mymoney.enums.CategoryIcon


data class CategoryIconItem(
        val categoryIcon: CategoryIcon,
        val isChecked: Boolean = false
)
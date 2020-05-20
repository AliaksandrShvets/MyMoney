package com.goodsoft.mymoney.implementations.backdrop.categories.editor

import com.goodsoft.mymoney.enums.CategoryIcon


data class CategoryIconItem(
        val categoryIcon: CategoryIcon,
        val isChecked: Boolean = false
)
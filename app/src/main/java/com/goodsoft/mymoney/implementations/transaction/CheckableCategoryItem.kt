package com.goodsoft.mymoney.implementations.transaction

import androidx.lifecycle.MutableLiveData
import com.goodsoft.mymoney.implementations.main.categories.CategoryItem

data class CheckableCategoryItem(
        val category: CategoryItem,
        var isSelected : MutableLiveData<Boolean>
)
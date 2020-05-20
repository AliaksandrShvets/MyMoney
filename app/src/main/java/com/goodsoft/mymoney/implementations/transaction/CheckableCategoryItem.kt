package com.goodsoft.mymoney.implementations.transaction

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.implementations.backdrop.categories.CategoryItem


data class CheckableCategoryItem(
        val category: CategoryItem,
        var isSelected : MutableLiveData<Boolean>
) {
    companion object {
        fun getDiffItemCallback() = object : DiffUtil.ItemCallback<CheckableCategoryItem>() {
            override fun areItemsTheSame(oldItem: CheckableCategoryItem, newItem: CheckableCategoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: CheckableCategoryItem, newItem: CheckableCategoryItem): Boolean {
                return oldItem.category == newItem.category &&
                        oldItem.isSelected.value == newItem.isSelected.value
            }
        }
    }
}
package com.goodsoft.mymoney.implementations.main.categories.editor

import android.annotation.SuppressLint
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.App
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.enums.CategoryIcon
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.*


class CategoryViewModel(val categoryId: String? = null) {

    val items: DiffObservableList<CategoryIconItem> = DiffObservableList(object : DiffUtil.ItemCallback<CategoryIconItem>() {
        override fun areItemsTheSame(oldItem: CategoryIconItem, newItem: CategoryIconItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryIconItem, newItem: CategoryIconItem): Boolean {
            return oldItem.categoryIcon == newItem.categoryIcon
                    && oldItem.isChecked == newItem.isChecked
        }
    })
    val name = ObservableField("")
    val nameError = ObservableField("")

    init {
        name.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                nameError.set("")
            }
        })
        categoryId?.let {
            getCategory(categoryId)
        } ?: run {
            updateItems()
        }
    }

    @SuppressLint("CheckResult")
    fun getCategory(categoryId: String) {
        CategoriesRoomRepository().getCategoryById(categoryId)
                .subscribe({ category ->
                    name.set(category.name)
                    updateItems(CategoryIcon.valueOf(category.icon))
                }, {})
    }

    fun updateItems(categoryIcon: CategoryIcon? = CategoryIcon.values()[0]) {
        val newList = CategoryIcon.values().map { CategoryIconItem(it, it == categoryIcon) }
        items.update(newList, items.calculateDiff(newList))
    }

    @SuppressLint("CheckResult")
    fun apply(): Boolean {
        if (name.get().isNullOrEmpty()) {
            nameError.set(App.res.getString(R.string.bottom_sheet_category_name_error))
            return false
        }
        val category = CategoryEntity(
                categoryId ?: UUID.randomUUID().toString(),
                name.get() ?: "",
                items.find { it.isChecked }?.categoryIcon?.name ?: ""
        )
        if (categoryId.isNullOrEmpty()) {
            CategoriesRoomRepository().addCategory(category).subscribe({}, {})
        } else {
            CategoriesRoomRepository().updateCategory(category).subscribe({}, {})
        }
        return true
    }

}
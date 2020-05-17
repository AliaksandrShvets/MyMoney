package com.goodsoft.mymoney.implementations.main.categories

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.goodsoft.mymoney.App
import com.goodsoft.mymoney.core.Constants
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.enums.CategoryIcon
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class CategoriesViewModel : ViewModel() {

    val items: DiffObservableList<CategoryItem> = DiffObservableList(object : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.categoryEntity == newItem.categoryEntity
                    && oldItem.icon == newItem.icon
        }
    })

    init {
        getCategories()
    }

    @SuppressLint("CheckResult")
    fun getCategories() {
        CategoriesRoomRepository().getCategories()
                .subscribe({ categoryList ->
                    val newList = categoryList.map {
                        CategoryItem(it, CategoryIcon.valueOf(it.icon).iconRes)
                    }
                    items.update(newList, items.calculateDiff(newList))
                }, {})
    }

    fun saveFavoriteCategory(favoriteCategoryId: String) {
        App.sharedPreferences.apply {
            val categoriesList = getString(Constants.KEY_FAVORITE_CATEGORIES, null)
                    ?.split(',')
                    ?.toMutableList() ?: mutableListOf()
            categoriesList.add(favoriteCategoryId)

            edit().putString(
                    Constants.KEY_FAVORITE_CATEGORIES,
                    categoriesList
                            .takeLast(Constants.COUNT_FAVORITE_CATEGORIES)
                            .joinToString(",")
            ).apply()
        }
    }

}
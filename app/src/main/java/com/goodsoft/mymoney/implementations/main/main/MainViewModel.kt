package com.goodsoft.mymoney.implementations.main.main

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.goodsoft.mymoney.App
import com.goodsoft.mymoney.core.Constants
import com.goodsoft.mymoney.core.toCategoryItem
import com.goodsoft.mymoney.database.tables.categories.CategoriesRoomRepository
import com.goodsoft.mymoney.implementations.main.categories.CategoryItem


class MainViewModel : ViewModel() {

    val favoriteCategoriesItems: ObservableArrayList<CategoryItem> = ObservableArrayList()

    init {
        initCategories(getFavoriteCategories())
    }

    @SuppressLint("CheckResult")
    private fun initCategories(categoriesId: List<String>) {
        CategoriesRoomRepository().getCategoriesByIds(categoriesId)
                .subscribe({ categoryList ->
                    categoriesId.forEach { categoryId ->
                        categoryList.find {categoryId == it.id }
                                ?.let { favoriteCategoriesItems.add(it.toCategoryItem()) }
                    }
                }, {})
    }

    private fun getFavoriteCategories(): List<String> {
        val categoryList = App.sharedPreferences
                .getString(Constants.KEY_FAVORITE_CATEGORIES, null)
                ?.split(',') ?: listOf()
        return categoryList
                .groupingBy { it }
                .eachCount()
                .map { it.value to it.key }
                .sortedByDescending { it.first }
                .map { it.second }
    }

}
package com.goodsoft.mymoney.database.tables.categories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface CategoriesRepository {

    fun getCategories() : Observable<List<CategoryEntity>>

    fun getCategoriesByIds(categoriesId: List<String>) : Single<List<CategoryEntity>>

    fun getCategoryById(categoryId: String) : Single<CategoryEntity>

    fun addCategory(categoryEntity: CategoryEntity) : Completable

    fun updateCategory(categoryEntity: CategoryEntity) : Completable

    fun deleteCategory(categoryEntity: CategoryEntity) : Completable

}
package com.goodsoft.mymoney.database.tables.categories

import com.goodsoft.mymoney.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CategoriesRoomRepository : CategoriesRepository {

    private val categoriesDao: CategoriesDao = App.dataBase.categoriesDao()

    override fun getCategories(): Observable<List<CategoryEntity>> = categoriesDao.getAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getCategoryById(categoryId: String) = categoriesDao.getCategoryById(categoryId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getCategoriesByIds(categoriesId: List<String>) = categoriesDao.getCategoriesByIds(categoriesId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun addCategory(categoryEntity: CategoryEntity) = categoriesDao.insert(categoryEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun updateCategory(categoryEntity: CategoryEntity) = categoriesDao.update(categoryEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteCategory(categoryEntity: CategoryEntity) = categoriesDao.delete(categoryEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}
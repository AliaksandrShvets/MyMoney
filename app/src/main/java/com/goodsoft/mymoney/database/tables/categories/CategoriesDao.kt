package com.goodsoft.mymoney.database.tables.categories

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface CategoriesDao {

    @Query("SELECT * from categoryEntity")
    fun getAll(): Observable<List<CategoryEntity>>

    @Query("SELECT * from categoryEntity WHERE id = :categoryId")
    fun getCategoryById(categoryId: String): Single<CategoryEntity>

    @Query("SELECT * from categoryEntity WHERE id IN (:categoriesIds)")
    fun getCategoriesByIds(categoriesIds: List<String>): Single<List<CategoryEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(categoryEntity: CategoryEntity): Completable

    @Update
    fun update(categoryEntity: CategoryEntity): Completable

    @Delete
    fun delete(categoryEntity: CategoryEntity): Completable

}
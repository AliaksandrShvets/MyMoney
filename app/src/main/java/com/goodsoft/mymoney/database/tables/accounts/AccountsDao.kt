package com.goodsoft.mymoney.database.tables.accounts

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface AccountsDao {

    @Query("SELECT * from accountEntity")
    fun getAll(): Observable<List<AccountEntity>>

    @Query("SELECT * from accountEntity WHERE id = :accountId")
    fun getAccountById(accountId: String): Single<AccountEntity>

    @Query("SELECT * from accountEntity WHERE id IN (:accountIds)")
    fun getAccountsByIds(accountIds: List<Long>): Single<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryEntity: AccountEntity): Completable

    @Update
    fun update(categoryEntity: AccountEntity): Completable

    @Delete
    fun delete(categoryEntity: AccountEntity): Completable

}
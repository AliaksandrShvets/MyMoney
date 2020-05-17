package com.goodsoft.mymoney.database.tables.transaction

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionsDao {

    @Query("SELECT * from transactionEntity")
    fun getAll(): Flow<List<TransactionEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(transactionEntity: TransactionEntity): Completable

    @Update
    fun update(transactionEntity: TransactionEntity): Completable

    @Delete
    fun delete(transactionEntity: TransactionEntity): Completable
}
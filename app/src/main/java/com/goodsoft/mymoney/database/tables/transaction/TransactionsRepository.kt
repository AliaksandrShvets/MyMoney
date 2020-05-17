package com.goodsoft.mymoney.database.tables.transaction

import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow


interface TransactionsRepository {

    fun getAll() : Flow<List<TransactionEntity>>

    fun insert(transactionEntity: TransactionEntity) : Completable

    fun update(transactionEntity: TransactionEntity) : Completable

    fun delete(transactionEntity: TransactionEntity) : Completable
}
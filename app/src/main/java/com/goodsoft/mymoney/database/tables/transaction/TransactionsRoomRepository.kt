package com.goodsoft.mymoney.database.tables.transaction

import com.goodsoft.mymoney.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TransactionsRoomRepository : TransactionsRepository {

    private val transactionsDao: TransactionsDao = App.dataBase.transactionsDao()

    override fun getAll() = transactionsDao.getAll()

    override fun insert(transactionEntity: TransactionEntity) =
            transactionsDao.insert(transactionEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun update(transactionEntity: TransactionEntity) =
            transactionsDao.update(transactionEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    override fun delete(transactionEntity: TransactionEntity) =
            transactionsDao.delete(transactionEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}
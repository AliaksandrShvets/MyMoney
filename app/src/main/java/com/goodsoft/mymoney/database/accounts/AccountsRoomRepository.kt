package com.goodsoft.mymoney.database.accounts

import com.goodsoft.mymoney.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AccountsRoomRepository : AccountsRepository {

    private val accountsDao: AccountsDao = App.dataBase.accountDao()

    override fun getAccounts(): Observable<List<AccountEntity>> = accountsDao.getAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getAccountById(accountId: String) = accountsDao.getAccountById(accountId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getAccountsByIds(accountsId: List<Long>) = accountsDao.getAccountsByIds(accountsId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun addAccount(accountEntity: AccountEntity) = accountsDao.insert(accountEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun updateAccount(accountEntity: AccountEntity) = accountsDao.update(accountEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteAccount(accountEntity: AccountEntity) = accountsDao.delete(accountEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}
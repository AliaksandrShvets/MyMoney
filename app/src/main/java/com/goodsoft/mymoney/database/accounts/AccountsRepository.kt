package com.goodsoft.mymoney.database.accounts

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface AccountsRepository {

    fun getAccounts() : Observable<List<AccountEntity>>

    fun getAccountsByIds(accountsId: List<Long>) : Single<List<AccountEntity>>

    fun getAccountById(accountId: String) : Single<AccountEntity>

    fun addAccount(accountEntity: AccountEntity) : Completable

    fun updateAccount(accountEntity: AccountEntity) : Completable

    fun deleteAccount(accountEntity: AccountEntity) : Completable

}
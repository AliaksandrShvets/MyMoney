package com.goodsoft.mymoney.database.tables.parsers

import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow


interface SmsParsersRepository {

    suspend fun getAll(): Flow<List<SmsParserEntity>>

    fun insert(smsParserEntity: SmsParserEntity): Completable

    fun update(smsParserEntity: SmsParserEntity): Completable

    fun delete(smsParserEntity: SmsParserEntity): Completable

}
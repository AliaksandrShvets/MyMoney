package com.goodsoft.mymoney.database.parsers

import com.goodsoft.mymoney.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow


class SmsParsersRoomRepository : SmsParsersRepository {

    private val smsParsersDao: SmsParsersDao = App.dataBase.smsParsers()

    override suspend fun getAll(): Flow<List<SmsParserEntity>> = smsParsersDao.getAll()

    override fun insert(smsParserEntity: SmsParserEntity) = smsParsersDao.insert(smsParserEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun update(smsParserEntity: SmsParserEntity) = smsParsersDao.update(smsParserEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun delete(smsParserEntity: SmsParserEntity) = smsParsersDao.delete(smsParserEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}
package com.goodsoft.mymoney.database.tables.parsers

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow


@Dao
interface SmsParsersDao {

    @Query("SELECT * from smsParserEntity")
    fun getAll(): Flow<List<SmsParserEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(parserEntity: SmsParserEntity): Completable

    @Update
    fun update(smsParserEntity: SmsParserEntity): Completable

    @Delete
    fun delete(smsParserEntity: SmsParserEntity): Completable

}
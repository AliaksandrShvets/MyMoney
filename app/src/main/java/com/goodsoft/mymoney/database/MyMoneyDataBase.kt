package com.goodsoft.mymoney.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.goodsoft.mymoney.database.converter.RoomConverters
import com.goodsoft.mymoney.database.tables.accounts.AccountsDao
import com.goodsoft.mymoney.database.tables.accounts.AccountEntity
import com.goodsoft.mymoney.database.tables.categories.CategoriesDao
import com.goodsoft.mymoney.database.tables.categories.CategoryEntity
import com.goodsoft.mymoney.database.tables.parsers.SmsParsersDao
import com.goodsoft.mymoney.database.tables.parsers.SmsParserEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionEntity
import com.goodsoft.mymoney.database.tables.transaction.TransactionsDao
import com.goodsoft.mymoney.enums.AccountIcon
import com.goodsoft.mymoney.enums.CategoryIcon
import java.util.*


@Database(
        entities = [
            CategoryEntity::class,
            AccountEntity::class,
            SmsParserEntity::class,
            TransactionEntity::class
        ],
        version = 1
)
@TypeConverters(RoomConverters::class)
abstract class MyMoneyDataBase : RoomDatabase() {

    companion object {
        fun newInstance(context: Context): MyMoneyDataBase = Room
                .databaseBuilder(context, MyMoneyDataBase::class.java, "MyMoneyDataBase")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        initCategories(db)
                        initAccounts(db)
                    }
                })
                .build()

        private fun initCategories(db: SupportSQLiteDatabase) {
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Ресторан', '${CategoryIcon.BAR.name}')")
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Такси', '${CategoryIcon.TAXI.name}')")
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Покупки', '${CategoryIcon.CHEQUE.name}')")
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Магазин', '${CategoryIcon.SHOP.name}')")
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Транспорт', '${CategoryIcon.TRANSPORT.name}')")
            db.execSQL("INSERT INTO categoryEntity values('${UUID.randomUUID()}', 'Дом', '${CategoryIcon.FLOWER.name}')")
        }

        private fun initAccounts(db: SupportSQLiteDatabase) {
            val currency = Currency.getInstance(Locale.getDefault()).currencyCode
            AccountIcon.values().forEachIndexed { index, accountIcon ->
                db.execSQL("INSERT INTO accountEntity values('$index', '${accountIcon.name}', '$currency', '${accountIcon.name}')")
            }
        }
    }

    abstract fun categoriesDao(): CategoriesDao

    abstract fun accountsDao(): AccountsDao

    abstract fun smsParsersDao(): SmsParsersDao

    abstract fun transactionsDao(): TransactionsDao

}
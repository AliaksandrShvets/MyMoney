package com.goodsoft.mymoney

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.facebook.stetho.Stetho
import com.goodsoft.mymoney.database.MyMoneyDataBase


class App : Application() {

    companion object {
        lateinit var dataBase: MyMoneyDataBase
            private set
        lateinit var sharedPreferences: SharedPreferences
            private set
        lateinit var res: Resources
            private set
    }

    override fun onCreate() {
        super.onCreate()
        dataBase = MyMoneyDataBase.newInstance(this)
        sharedPreferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        res = applicationContext.resources
        setupStetho(this)
    }

    private fun setupStetho(context: Context) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context)
        }
    }

}
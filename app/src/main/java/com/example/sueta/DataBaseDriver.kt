package com.example.sueta

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.sueta.database.SuetaDatabase


class DataBaseDriver( context : Context){


    private val driver = AndroidSqliteDriver(
        schema = SuetaDatabase.Schema,
        context = context,
        name = "sueta.db"
    )
    val database = SuetaDatabase(driver)



}
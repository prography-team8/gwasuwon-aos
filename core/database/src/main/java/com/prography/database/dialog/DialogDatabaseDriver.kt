package com.prography.database.dialog

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/**
 * Created by MyeongKi.
 */
fun provideDialogDatabaseDriver(context: Context): SqlDriver = AndroidSqliteDriver(
    schema = GwasuwonDialogSqlDelightDatabase.Schema,
    context = context,
    name = "GwasuwonDialogSqlDelightDatabase.db"
)

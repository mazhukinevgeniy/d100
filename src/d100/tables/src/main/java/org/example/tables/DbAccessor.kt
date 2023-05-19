package org.example.tables

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.Database

public object DbAccessor {
    private val driver: SqlDriver by lazy {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:d100db.sql")
        Database.Schema.create(driver)
        return@lazy driver
    }

    val database: Database by lazy {
        Database(driver)
    }
}

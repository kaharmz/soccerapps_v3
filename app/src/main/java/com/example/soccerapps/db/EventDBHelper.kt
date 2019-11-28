package com.example.soccerapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class EventDBHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(
        ctx,
        "FavoriteEvent.db",
        null,
        1
    ) {

    companion object {
        private var instance: EventDBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): EventDBHelper {
            if (instance == null) {
                instance = EventDBHelper(ctx.applicationContext)
            }
            return instance as EventDBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            EventDB.TABLE_EVENT, true,
            EventDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EventDB.EVENT_ID to TEXT + UNIQUE,
            EventDB.EVENT_DATE to TEXT,
            EventDB.HOME_TEAM_ID to TEXT,
            EventDB.HOME_TEAM to TEXT,
            EventDB.HOME_SCORE to TEXT,
            EventDB.AWAY_TEAM_ID to TEXT,
            EventDB.AWAY_TEAM to TEXT,
            EventDB.AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(EventDB.TABLE_EVENT, true)
    }
}

val Context.database: EventDBHelper
    get() = EventDBHelper.getInstance(applicationContext)
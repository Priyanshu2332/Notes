package com.example.internshalanotes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {   //companion object is used to define singleton object
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_LOGGED_IN = "logged_in" // Added column for logged-in state
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CreateTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_LOGGED_IN INTEGER DEFAULT 0)") // Default to 0 (false) for logged-in state
        db?.execSQL(CreateTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(email: String): Boolean {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, email)
            put(COLUMN_LOGGED_IN, 0) // Ensure logged-in state is initialized to 0 (false)
        }
        val db = writableDatabase
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    fun checkUserExists(email: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    fun markUserAsLoggedIn(email: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LOGGED_IN, 1) // Mark user as logged in (1)
        }
        val whereClause = "$COLUMN_USERNAME = ?"
        val whereArgs = arrayOf(email)
        db.update(TABLE_NAME, values, whereClause, whereArgs)
    }

    fun isAnyUserLoggedIn(): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_LOGGED_IN = ?"
        val selectionArgs = arrayOf("1") // Check for logged-in users
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }
}

package com.example.internshalanotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.internshalanotes.Dao.NotesDao
import com.example.internshalanotes.Model.Notes


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun myNotesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(context.applicationContext,
                    NotesDatabase::class.java,
                    "Notes").allowMainThreadQueries().build()   //allow main thread means its allowing to access the UI of main wihtout this cant run create note sfragment
                INSTANCE = instance
                return instance
            }
        }
    }
}

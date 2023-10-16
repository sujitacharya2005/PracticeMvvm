package com.example.practiceingeneral.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null
        private const val DB_NAME = "note_database"

        fun getDatabase(@Suppress("unused") context: Context): NoteDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

        private val callback : Callback = object :Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE!!.noteDao()).execute()
            }
        }
        class PopulateDbAsyncTask(private val noteDao: NoteDao) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.insert(Note(title = "Title 1", description = "Description 1", priority = 1))
                noteDao.insert(Note(title = "Title 2", description = "Description 2", priority = 2))
                noteDao.insert(Note(title = "Title 3", description = "Description 3", priority = 3))
                return null
            }
        }

    }
}
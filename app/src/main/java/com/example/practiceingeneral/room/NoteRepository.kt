package com.example.practiceingeneral.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NoteRepository(application: Application) {
    private val noteDao:NoteDao
    private val allNotes: LiveData<List<Note>>
    init {
        val noteDb = NoteDatabase.getDatabase(application)
        noteDao = noteDb.noteDao()
        allNotes = noteDao.getAllNotes()

    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes() : LiveData<List<Note>> {
        return  allNotes
    }

    companion object {
        class InsertNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.insert(params[0]!!)
                return null
            }
        }

        class UpdateNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.update(params[0]!!)
                return null
            }
        }

        class DeleteNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.delete(params[0]!!)
                return null
            }
        }

        class DeleteAllNotesAsyncTask(private val noteDao: NoteDao) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.deleteAllNotes()
                return null
            }
        }
    }
}
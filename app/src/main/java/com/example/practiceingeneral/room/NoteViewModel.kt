package com.example.practiceingeneral.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository:NoteRepository
    private val allNotes: LiveData<List<Note>>

    init {
        noteRepository = NoteRepository(application)
        allNotes = noteRepository.getAllNotes()
    }
    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }
    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}
package com.example.practiceingeneral.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.practiceingeneral.room.Note
import com.example.practiceingeneral.repo.NoteRepository
import javax.inject.Inject

class NoteViewModel @Inject constructor(application: Application, val initialValue: Int) : AndroidViewModel(application) {
    private val noteRepository: NoteRepository
    private val allNotes: LiveData<List<Note>>
    var count : Int = initialValue

    fun increment() {
        count++;
    }

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
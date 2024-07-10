package com.example.internshalanotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.internshalanotes.Model.Notes
import com.example.internshalanotes.Repository.NotesRepo
import com.example.internshalanotes.db.NotesDatabase

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepo

    init {
        // Initialize repository with DAO instance from database
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepo(dao)
    }

    // Function to add a new note
    fun addNotes(notes: Notes) {
        repository.insertnote(notes)
    }
    // Function to get all notes from the database
    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    // Function to delete a note by its ID
    fun deleteNotes(id: Int) {
        repository.deletenotes(id)
    }

    // Function to update an existing note
    fun updateNotes(notes: Notes){
        repository.updatenotes(notes)
    }
}

package com.example.internshalanotes.Repository

import com.example.internshalanotes.Dao.NotesDao
import com.example.internshalanotes.Model.Notes
import androidx.lifecycle.LiveData

class NotesRepo(val dao: NotesDao) {

    fun getAllNotes():LiveData<List<Notes>> =dao.GetNotes()

    fun insertnote(notes: Notes)
    {
        dao.InsertNotes(notes)
    }
    fun deletenotes(id:Int){
        dao.DeleteNotes(id)
    }
    fun updatenotes(notes: Notes){
        dao.UpdateNotes(notes)
    }

}
package com.example.internshalanotes.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.internshalanotes.Model.Notes

@Dao
interface NotesDao {
    @Query( "SELECT * FROM Notes")
    fun GetNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id = :id")
    fun DeleteNotes(id:Int)

    @Update
    fun UpdateNotes(notes: Notes)
}
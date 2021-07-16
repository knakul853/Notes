package com.knakul853.mysecretdiary.data.note

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note:Note)

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note:Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNote()

    @Query("SELECT * FROM notes_table")
    fun getAllNotes():LiveData<List<Note>>
}
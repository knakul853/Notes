package com.knakul853.mysecretdiary.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.knakul853.mysecretdiary.data.note.Note
import com.knakul853.mysecretdiary.data.note.NoteDao
import com.knakul853.mysecretdiary.data.note.NoteDataBase
import com.knakul853.mysecretdiary.utils.provideApplicationScope
import kotlinx.coroutines.launch

class NoteRepository(application: Application) {

    lateinit var allNotes:LiveData<List<Note>>
    lateinit var noteDao:NoteDao
    val TAG = "NoteRepository"


    init {
        Log.i(TAG, "application: ${application.applicationContext}")
        val db = NoteDataBase.getDatabaseInstance(application.applicationContext)
        noteDao = db.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note:Note){
        provideApplicationScope().launch {
            noteDao.insert(note)
        }
    }
    fun update(note:Note){
        provideApplicationScope().launch {
            noteDao.update(note)
        }
    }
    fun delete(note:Note){
        provideApplicationScope().launch {
            noteDao.delete(note)
        }
    }
    fun deleteAllNotes(){
        provideApplicationScope().launch {
            noteDao.deleteAllNote()
        }
    }

    fun getAllNote(): LiveData<List<Note>>{
        provideApplicationScope().launch {
             allNotes = noteDao.getAllNotes()
        }
        return allNotes
    }

}
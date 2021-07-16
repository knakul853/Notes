package com.knakul853.mysecretdiary.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knakul853.mysecretdiary.data.note.Note

class HomeViewModel(app:Application) : AndroidViewModel(app) {
    private val repository = NoteRepository(app)

    private val allNotes = repository.getAllNote()

    fun insert(note: Note){
        repository.insert(note)
    }

    fun update(note:Note){
        repository.update(note)
    }

    fun delete(note:Note){
        repository.delete(note)
    }

    fun deleteAll(){
        repository.deleteAllNotes()
    }

    fun getAllNote():LiveData<List<Note>>{
        return allNotes
    }

}
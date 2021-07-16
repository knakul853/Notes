package com.knakul853.mysecretdiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knakul853.mysecretdiary.R
import com.knakul853.mysecretdiary.data.note.Note

class NoteAdapter(private val onItemClick:(Note)->Unit): ListAdapter<Note, NoteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_note, parent, false)
        return NoteViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

            val curentItem = getItem(position)
            holder.title.text = curentItem.title
            holder.description.text = curentItem.description
            holder.date.text = curentItem.createAt

        holder.bind(curentItem)
    }

    fun getNote(position: Int):Note{
        return getItem(position)
    }

}

private val diffUtil  = object: DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title && oldItem.description == newItem.description
    }

}

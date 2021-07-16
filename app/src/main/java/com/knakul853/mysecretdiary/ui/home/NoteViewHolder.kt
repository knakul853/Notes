package com.knakul853.mysecretdiary.ui.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knakul853.mysecretdiary.R
import com.knakul853.mysecretdiary.data.note.Note

class NoteViewHolder(itemView: View, private val onItemClicked:(Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(note: Note) {
        itemView.setOnClickListener {
            onItemClicked(note)
        }
    }

    val title = itemView.findViewById<TextView>(R.id.note_title_text_view)
    val description = itemView.findViewById<TextView>(R.id.note_description_text_view)
    val date = itemView.findViewById<TextView>(R.id.date_text_view)
}
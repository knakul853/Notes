package com.knakul853.mysecretdiary.data.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "notes_table")
@Parcelize
data class Note(

    @ColumnInfo(name="note_title")
     var title:String,

    @ColumnInfo(name="note_description")
     var description:String,

    @ColumnInfo(name = "note_date")
     var createAt:String = SimpleDateFormat("dd / MM / yyyy").format(System.currentTimeMillis()),
    @PrimaryKey(autoGenerate = true)
    val id:Int=0

):Parcelable {

}

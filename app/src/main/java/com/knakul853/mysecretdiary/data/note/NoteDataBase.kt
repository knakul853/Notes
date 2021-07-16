package com.knakul853.mysecretdiary.data.note

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.knakul853.mysecretdiary.utils.provideApplicationScope
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class], version = 2,
)
abstract class NoteDataBase: RoomDatabase() {

    abstract fun noteDao():NoteDao

    companion object{
        private var instance : NoteDataBase?=null

        @Synchronized
        fun getDatabaseInstance(context: Context): NoteDataBase{

            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "notes_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            }

            return instance!!
        }

        private val roomCallback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                populateInitialData(instance!!)
            }
        }



        private fun populateInitialData(db: NoteDataBase) {

            val noteDao = db.noteDao()
            provideApplicationScope().launch {
                noteDao.insert(Note("Title", "description"))

            }

        }
    }


}
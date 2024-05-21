package com.example.notebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// قسمت ساخت دیتابیس
// اینجا به room میگیم این جدول های دیتابیسمه، اینم ورژنشه، اینم عملیات های اونه
@Database(entities = [NoteTable::class], version = 1)
abstract class DBhandler : RoomDatabase() {
    abstract fun NoteDao(): NoteDAO


    // ساخت یک مدل از دیتابیس
    companion object{// اطلاعات داخل companion object از حافظه ram پاک نمیشن
        private var existence: DBhandler?= null
        fun getDatabse(c: Context): DBhandler {
            if(existence == null)
                existence = Room.databaseBuilder(c,
                    DBhandler::class.java, "localDB").fallbackToDestructiveMigration().build() //کد ساخت دیتابیس
            return existence!! // بالاخره اینجا دیتابیس ساخته شده رو برمیگردونه تا ما بتونیم باهاش کار کنیم.
        }
    }

}
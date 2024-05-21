package com.example.notebook.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// عملیات های جدول
@Dao
interface NoteDAO {

    @Insert
    fun insertNote(table: NoteTable)

    @Update
    fun UpdateNote(table: NoteTable)

    @Query("UPDATE NoteTable SET deleteState = 'true' WHERE id = :i")
    fun UpdateDeleteStateToTrue(i:Int)

    @Query("UPDATE NoteTable SET deleteState = 'false' WHERE id = :i")
    fun UpdateDeleteStateToFalse( i:Int)

    @Delete
    fun DeleteUser(table: NoteTable)

    @Query("delete from NoteTable")
    fun DeleteAllUsers()





    @Query("select * from NoteTable")
    fun getAllNotes(): Flow<List<NoteTable>>

    @Query("select * from NoteTable where deleteState = 'true'")
    fun getAllNotesTRUE(): Flow<List<NoteTable>>

    @Query("select * from NoteTable where deleteState = 'false'")
    fun getAllNotesFALSE() : Flow<List<NoteTable>> // فعلا بلد نیستم فقط title و description رو ازش بگیرم، آخه باید این لاین کد رو جوری تغییر بدم که فقط همونارو بفرسته.

    @Query("select * from NoteTable where id =:s")
    fun getNoteWithID(s:Int?): NoteTable
}
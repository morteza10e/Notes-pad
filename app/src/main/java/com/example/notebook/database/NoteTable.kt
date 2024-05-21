package com.example.notebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// ساخت جدول User
@Entity
data class NoteTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val date: String,
    @ColumnInfo val deleteState: String)

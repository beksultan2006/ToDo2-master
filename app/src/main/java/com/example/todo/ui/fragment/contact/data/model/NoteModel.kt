package com.example.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var image: String? = null,
    var title: String? = null,
    var description: String? = null,
    var date: String
)

package com.samarth.todocompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// Todo Entity
@Entity
data class Todo(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    @PrimaryKey
    val id: Int? = null
)

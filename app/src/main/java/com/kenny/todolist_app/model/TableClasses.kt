package com.kenny.todolist_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)

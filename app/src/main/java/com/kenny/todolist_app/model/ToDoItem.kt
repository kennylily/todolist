package com.kenny.todolist_app.model

data class ToDoItem(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)
data class ToDoItemInsertReq(
    val todo: String,
    val completed: Boolean,
    val userId: Int
)
data class ToDoItemDeleteResp(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int,
    val isDeleted: Boolean,
    val deletedOn: String
)

data class ApiResonse(
    val todos: List<ToDoItem>
)

// 定義 UpdateRequest 數據類型
data class UpdateRequest(
    val completed: Boolean // 更新的完成狀態
)
package com.kenny.todolist_app.apimanager

import com.kenny.todolist_app.model.ApiResonse
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.model.ToDoItemDeleteResp
import com.kenny.todolist_app.model.ToDoItemInsertReq
import com.kenny.todolist_app.model.UpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiList {
    @GET("todos")
    suspend fun getToDos(): ApiResonse

    // 獲取待辦事項詳細信息
    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): ToDoItem

    // 更新待辦事項
    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int, // 待辦事項的 ID
        @Body UpdateRequest: UpdateRequest // 要更新的待辦事項對象
    ): ToDoItem

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): ToDoItemDeleteResp

    // 添加新的待辦事項
    @POST("todos/add")
    suspend fun addTodo(@Body todo: ToDoItemInsertReq): ToDoItem


}
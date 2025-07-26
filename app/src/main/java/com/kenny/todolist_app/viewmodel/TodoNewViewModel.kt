package com.kenny.todolist_app.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.todolist_app.apimanager.RetrofitInstance
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.model.ToDoItemInsertReq
import com.kenny.todolist_app.model.UpdateRequest
import kotlinx.coroutines.launch

class TodoNewViewModel: ViewModel() {
    private val _todoDetail = MutableLiveData<ToDoItem>()
    val todoDetail: LiveData<ToDoItem> get() = _todoDetail

    fun insertToDoDetail(TodoItemInsertReq: ToDoItemInsertReq) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addTodo(TodoItemInsertReq) // 假設你要添加一個新的待辦事項
                // 假設你要更新待辦事項的完成狀態
                _todoDetail.value = response
            } catch (e: Exception) {
                // 處理錯誤
                Log.e("insertToDoDetail", "Error updating todo: ${e}")
            }
        }

    }
}
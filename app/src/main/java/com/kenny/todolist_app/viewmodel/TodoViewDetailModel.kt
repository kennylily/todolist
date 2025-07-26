package com.kenny.todolist_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.todolist_app.apimanager.RetrofitInstance
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.model.UpdateRequest
import kotlinx.coroutines.launch

class TodoViewDetailModel: ViewModel() {
    private val _todoDetail = MutableLiveData<ToDoItem>()
    val todoDetail: LiveData<ToDoItem> get() = _todoDetail
    private val _todoModifySuccess = MutableLiveData<Boolean>()
    val todoModifySuccess: LiveData<Boolean> get() = _todoModifySuccess

    fun fetchToDosDetail(taskID: Int){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTodoById(id = taskID) // 假設你要獲取 ID 為 1 的待辦事項
                _todoDetail.value = response
            } catch (e: Exception) {
                // 處理錯誤
                Log.e("TodoViewModel", "Error fetching todos: ${e.message}")
            }
        }
    }
    fun updateTodoDetail(todoItem: ToDoItem) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateTodo(id = todoItem.id,
                    UpdateRequest = UpdateRequest(completed = todoItem.completed)
                ) // 假設你要更新待辦事項的完成狀態
                //_todoDetail.value = response
                _todoModifySuccess.value = true // 更新成功
            } catch (e: Exception) {
                // 處理錯誤
                _todoModifySuccess.value = false
                Log.e("TodoViewModel", "Error updating todo: ${e}")
            }
        }
    }
    fun deleteTodoDetail(taskID: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteTodo(id = taskID)
                if (response.isDeleted) {
                    _todoModifySuccess.value = true // 刪除成功
                } else {
                    _todoModifySuccess.value = false // 刪除失敗
                }
            } catch (e: Exception) {
                // 處理錯誤
                _todoModifySuccess.value = false
                Log.e("TodoViewModel", "Error deleting todo: ${e.message}")
            }
        }
    }
}
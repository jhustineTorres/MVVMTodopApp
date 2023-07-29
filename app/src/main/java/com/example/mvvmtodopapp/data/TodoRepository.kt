package com.example.mvvmtodopapp.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository { // This decide between local data (cache) and data from api (server) what will it forward for viewModel

    suspend fun insertTodo(todo: Todo) //suspend fun suspend the activity until get a results

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo? // return null if did not exist to not crash the app

    fun getTodos(): Flow<List<Todo>>
}
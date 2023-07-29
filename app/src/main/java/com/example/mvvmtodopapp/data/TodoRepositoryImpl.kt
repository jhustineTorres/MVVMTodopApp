package com.example.mvvmtodopapp.data

import kotlinx.coroutines.flow.Flow

// this implement TodoRepository
class TodoRepositoryImpl(
    private val dao: TodoDao //give access to actual database instance
): TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }

}
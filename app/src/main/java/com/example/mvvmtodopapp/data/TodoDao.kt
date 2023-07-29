package com.example.mvvmtodopapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao { // Dao means "Data access object" witch define diff ways to access our databse

    @Insert(onConflict = OnConflictStrategy.REPLACE) // replace the old one if the id is same so that we can have insert and update action just in one function
    suspend fun insertTodo(todo: Todo) //suspend fun suspend the activity until get a results

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id")// use colon ":" like :id to refer in parameter
    suspend fun getTodoById(id: Int): Todo? // return null if did not exist to not crash the app

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>
}
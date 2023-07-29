package com.example.mvvmtodopapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

//@Database(
//    entities = [Todo::class],
//    version = 1 // update this if database changes apply so room can recognize if its need to migrate its data or not
//)
//abstract class TodoDatabase: RoomDatabase() { // inherit from RoomDatabas
//abstract val dao: TodoDao
//}

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {

    abstract val dao: TodoDao
}
package com.example.mvvmtodopapp.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodopapp.data.Todo
import com.example.mvvmtodopapp.data.TodoRepository
import com.example.mvvmtodopapp.util.Routes
import com.example.mvvmtodopapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Every view model in android inherits from ViewModel that give some life cycle specific behavior
@HiltViewModel
class TodoListViewModel @Inject constructor( //@Inject so daggerhilt to look in modules. if there's dependencies of this type that is can create and can be pass behind the scenes
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null //cache deleted T0DO if undo we can insert this back to database

    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                    }
                }
            }
            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo // save T0DO for Undo function
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackbar( // Show undo Snackbar
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo( // this will not insert data in database because in TodoDao if we try to insert a data that exist it will only update it
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
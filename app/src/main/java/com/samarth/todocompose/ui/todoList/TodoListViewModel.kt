package com.samarth.todocompose.ui.todoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.todocompose.data.Todo
import com.samarth.todocompose.data.TodoRepository
import com.samarth.todocompose.utils.Routes
import com.samarth.todocompose.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todo = repository.getTodo()
    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var deletedTodo: Todo? = null
    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.delTodo(event.todo)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Todo Deleted", "Undo"
                        )
                    )
                }
            }

            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId= ${event.todo.id}"))
            }

            is TodoListEvent.OnUndoClicked -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                    }
                }
            }
        }
    }

    fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvents.send(uiEvent)
        }
    }
}
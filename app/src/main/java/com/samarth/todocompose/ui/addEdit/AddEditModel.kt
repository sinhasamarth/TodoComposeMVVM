package com.samarth.todocompose.ui.addEdit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.todocompose.data.Todo
import com.samarth.todocompose.data.TodoRepository
import com.samarth.todocompose.ui.todoList.TodoListEvent
import com.samarth.todocompose.utils.Routes
import com.samarth.todocompose.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        todoId?.let {
            if (it != -1) {
                viewModelScope.launch {
                    repository.getTodo(it)?.let {
                        title = it.title
                        description = it.description ?: ""
                        this@AddEditModel.todo = it
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnDescriptionChanged -> {
                description = event.description
            }

            is AddEditEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                "Title is not empty"
                            )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }

            is AddEditEvent.OnTitleChanged -> {
                title = event.title
                Log.d("VALUESAM", title.toString())
            }
        }

    }

    fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvents.send(uiEvent)
        }
    }
}
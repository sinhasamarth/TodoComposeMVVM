package com.samarth.todocompose.ui.todoList

import com.samarth.todocompose.data.Todo

sealed class TodoListEvent {
    data class OnDeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean) : TodoListEvent()
    object OnUndoClicked : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    object OnAddTodoClick : TodoListEvent()
}
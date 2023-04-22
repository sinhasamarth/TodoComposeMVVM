package com.samarth.todocompose.ui.addEdit

sealed class AddEditEvent {
    data class OnTitleChanged(val title: String) : AddEditEvent()
    data class OnDescriptionChanged(val description: String) : AddEditEvent()
    object OnSaveTodoClick : AddEditEvent()
}
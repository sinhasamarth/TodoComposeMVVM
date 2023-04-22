package com.samarth.todocompose.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insertTodo(todo: Todo)

    suspend fun delTodo(todo: Todo)

    suspend fun getTodo(id: Int): Todo?

    fun getTodo(): Flow<List<Todo>>
}
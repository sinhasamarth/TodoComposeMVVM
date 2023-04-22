package com.samarth.todocompose.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImp(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override suspend fun delTodo(todo: Todo) = dao.delTodo(todo)

    override suspend fun getTodo(id: Int) = dao.getTodo(id)


    override fun getTodo() = dao.getTodo()
}
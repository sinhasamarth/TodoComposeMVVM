package com.samarth.todocompose.di

import android.app.Application
import androidx.room.Room
import com.samarth.todocompose.data.TodoDatabase
import com.samarth.todocompose.data.TodoRepository
import com.samarth.todocompose.data.TodoRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideTodoRepository(todo: TodoDatabase): TodoRepository = TodoRepositoryImp(todo.dao)
}
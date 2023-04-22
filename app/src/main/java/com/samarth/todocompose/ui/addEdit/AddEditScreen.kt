package com.samarth.todocompose.ui.addEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samarth.todocompose.utils.UiEvent

@Composable
fun AddEditTodoScreen(
    onPopUpBackStack: () -> Unit,
    addEditModel: AddEditModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        addEditModel.uiEvents.collect { event ->

            when (event) {
                is UiEvent.Navigate -> Unit
                is UiEvent.PopBackStack -> onPopUpBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }

        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            ),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addEditModel.onEvent(AddEditEvent.OnSaveTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TextField(
                value = addEditModel.title,
                onValueChange = { data ->
                    addEditModel.onEvent(AddEditEvent.OnTitleChanged(data))
                },
                placeholder = {
                    Text("Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = addEditModel.description,
                onValueChange = { data ->
                    addEditModel.onEvent(AddEditEvent.OnDescriptionChanged(data))
                },
                placeholder = {
                    Text("Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}
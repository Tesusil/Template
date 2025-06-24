package com.tesusil.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.LoadUsers)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.onEvent(HomeEvent.RefreshUsers()) }) {
                Text("Odśwież")
            }
            Button(onClick = { viewModel.onEvent(HomeEvent.CreateRandomUser) }) {
                Text("Dodaj losowego")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Text(
                text = "Błąd: ${state.error?.localizedMessage ?: "Nieznany błąd"}",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LazyColumn {
                items(state.users) { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(user.userName)
                        Row {
                            Button(
                                onClick = { viewModel.onEvent(HomeEvent.SelectUser(user.userId)) }
                            ) {
                                Text("Szczegóły")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { viewModel.onEvent(HomeEvent.DeleteUser(user.userId)) }
                            ) {
                                Text("Usuń")
                            }
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

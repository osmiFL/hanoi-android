package com.osmi.hanoitowers.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.osmi.hanoitowers.presentation.viewmodel.HanoiViewModel
import com.osmi.hanoitowers.presentation.ui.components.TowersView

@Composable
fun MainView(
    viewModel: HanoiViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Torres de Hanoi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Número de discos:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = if (uiState.diskInput == 0) "" else uiState.diskInput.toString(),
                    onValueChange = { viewModel.updateDiskInput(it) },
                    label = { Text("Discos") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { viewModel.searchHanoiSolution() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.diskInput > 0
                ) {
                    Text("Buscar Solución")
                }
            }
        }
        
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando...")
                }
            }
            
            uiState.error != null -> {
                ErrorContent(
                    error = uiState.error,
                    onRetry = { 
                        val disks = if (uiState.diskInput > 0) uiState.diskInput else 1
                        viewModel.loadHanoiSolution(disks, 1) 
                    },
                    onDismiss = { viewModel.clearError() }
                )
            }
            
            uiState.hanoiResponse != null && uiState.hasSearched -> {
                TowersView(
                    towerA = uiState.towerA,
                    towerB = uiState.towerB,
                    towerC = uiState.towerC,
                    currentMoveIndex = uiState.currentMoveIndex,
                    totalMoves = uiState.hanoiResponse?.totalMovements ?: 0,
                    currentUrl = uiState.hanoiResponse?.urls?.current ?: "",
                    onNextMove = { viewModel.nextMove() },
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}

@Composable
private fun ErrorContent(
    error: String?,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onRetry) {
                    Text("Reintentar")
                }
                
                OutlinedButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        }
    }
}

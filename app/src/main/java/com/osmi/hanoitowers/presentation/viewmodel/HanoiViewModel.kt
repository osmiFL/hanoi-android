package com.osmi.hanoitowers.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmi.hanoitowers.domain.model.HanoiResponse
import com.osmi.hanoitowers.domain.usecase.GetHanoiSolutionUseCase
import com.osmi.hanoitowers.presentation.state.HanoiUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HanoiViewModel(
    private val getHanoiSolutionUseCase: GetHanoiSolutionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HanoiUiState())
    val uiState: StateFlow<HanoiUiState> = _uiState.asStateFlow()

    fun loadHanoiSolution(disks: Int, page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val response = getHanoiSolutionUseCase(disks, page)
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        hanoiResponse = response,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun updateDiskInput(input: String) {
        val diskCount = input.toIntOrNull() ?: 0
        _uiState.update { it.copy(diskInput = diskCount) }
    }

    fun searchHanoiSolution() {
        val disks = uiState.value.diskInput
        if (disks > 0) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                try {
                    val response = getHanoiSolutionUseCase(disks, 1)
                    val initialTowerA = (1..disks).toList()
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            hanoiResponse = response,
                            hasSearched = true,
                            currentMoveIndex = 0,
                            currentPage = 1,
                            towerA = initialTowerA,
                            towerB = emptyList(),
                            towerC = emptyList()
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }
    }

    fun nextMove() {
        val currentState = _uiState.value
        val response = currentState.hanoiResponse
        val currentIndex = currentState.currentMoveIndex
        
        if (response != null && currentIndex < response.movements.size) {
            val movement = response.movements[currentIndex]
            val newTowerA = currentState.towerA.toMutableList()
            val newTowerB = currentState.towerB.toMutableList()
            val newTowerC = currentState.towerC.toMutableList()
            
            when (movement.from) {
                "A" -> newTowerA.remove(movement.disk)
                "B" -> newTowerB.remove(movement.disk)
                "C" -> newTowerC.remove(movement.disk)
            }
            
            when (movement.to) {
                "A" -> newTowerA.add(movement.disk)
                "B" -> newTowerB.add(movement.disk)
                "C" -> newTowerC.add(movement.disk)
            }
            
            newTowerA.sort()
            newTowerB.sort()
            newTowerC.sort()
            
            _uiState.update { 
                it.copy(
                    currentMoveIndex = currentIndex + 1,
                    towerA = newTowerA,
                    towerB = newTowerB,
                    towerC = newTowerC
                )
            }
            
            val remainingMoves = response.movements.size - (currentIndex + 1)
            if (remainingMoves <= 20 && response.hasNext) {
                loadNextPage()
            }
        }
    }
    
    private fun loadNextPage() {
        val currentState = _uiState.value
        val response = currentState.hanoiResponse
        val nextPage = currentState.currentPage + 1
        
        if (response != null && response.hasNext) {
            viewModelScope.launch {
                try {
                    val nextResponse = getHanoiSolutionUseCase(currentState.diskInput, nextPage)
                    
                    val combinedMovements = response.movements + nextResponse.movements
                    
                    _uiState.update { 
                        it.copy(
                            hanoiResponse = response.copy(
                                movements = combinedMovements,
                                hasNext = nextResponse.hasNext,
                                page = nextPage,
                                urls = nextResponse.urls
                            ),
                            currentPage = nextPage
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            error = e.message ?: "Error al cargar más movimientos"
                        )
                    }
                }
            }
        }
    }
}

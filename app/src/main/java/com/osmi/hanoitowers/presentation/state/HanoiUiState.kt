package com.osmi.hanoitowers.presentation.state

import com.osmi.hanoitowers.domain.model.HanoiResponse

data class HanoiUiState(
    val isLoading: Boolean = false,
    val hanoiResponse: HanoiResponse? = null,
    val error: String? = null,
    val diskInput: Int = 0,
    val hasSearched: Boolean = false,
    val currentMoveIndex: Int = 0,
    val currentPage: Int = 1,
    val towerA: List<Int> = emptyList(),
    val towerB: List<Int> = emptyList(),
    val towerC: List<Int> = emptyList()
)

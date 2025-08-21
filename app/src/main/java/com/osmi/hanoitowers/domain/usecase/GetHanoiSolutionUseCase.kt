package com.osmi.hanoitowers.domain.usecase

import com.osmi.hanoitowers.domain.model.HanoiResponse
import com.osmi.hanoitowers.domain.repository.HanoiRepository

class GetHanoiSolutionUseCase(
    private val repository: HanoiRepository
) {
    suspend operator fun invoke(disks: Int, page: Int): HanoiResponse {
        return repository.getHanoiSolution(disks, page)
    }
}

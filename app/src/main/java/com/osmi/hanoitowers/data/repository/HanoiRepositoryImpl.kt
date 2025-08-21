package com.osmi.hanoitowers.data.repository

import com.osmi.hanoitowers.data.remote.HanoiApiService
import com.osmi.hanoitowers.domain.model.HanoiResponse
import com.osmi.hanoitowers.domain.repository.HanoiRepository

class HanoiRepositoryImpl(
    private val apiService: HanoiApiService
) : HanoiRepository {
    
    override suspend fun getHanoiSolution(disks: Int, page: Int): HanoiResponse {
        return apiService.getHanoiSolution(disks, page)
    }
}

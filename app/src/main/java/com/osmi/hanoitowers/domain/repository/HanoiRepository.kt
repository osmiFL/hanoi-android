package com.osmi.hanoitowers.domain.repository

import com.osmi.hanoitowers.domain.model.HanoiResponse

interface HanoiRepository {
    suspend fun getHanoiSolution(disks: Int, page: Int): HanoiResponse
}

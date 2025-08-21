package com.osmi.hanoitowers.data.remote

import com.osmi.hanoitowers.domain.model.HanoiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HanoiApiService {
    @GET("api/hanoi/{disks}")
    suspend fun getHanoiSolution(
        @Path("disks") disks: Int,
        @Query("page") page: Int
    ): HanoiResponse
}

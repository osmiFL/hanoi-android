package com.osmi.hanoitowers.di

import com.osmi.hanoitowers.data.remote.RetrofitConfig
import com.osmi.hanoitowers.data.repository.HanoiRepositoryImpl
import com.osmi.hanoitowers.domain.repository.HanoiRepository
import com.osmi.hanoitowers.domain.usecase.GetHanoiSolutionUseCase
import com.osmi.hanoitowers.presentation.viewmodel.HanoiViewModel

object DependencyInjection {
    
    private val hanoiRepository: HanoiRepository by lazy {
        HanoiRepositoryImpl(RetrofitConfig.hanoiApiService)
    }
    
    private val getHanoiSolutionUseCase: GetHanoiSolutionUseCase by lazy {
        GetHanoiSolutionUseCase(hanoiRepository)
    }
    
    fun provideHanoiViewModel(): HanoiViewModel {
        return HanoiViewModel(getHanoiSolutionUseCase)
    }
}

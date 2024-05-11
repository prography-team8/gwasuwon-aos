package com.prography.usm_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.usecase.SaveCurrentCountUseCase

/**
 * Created by MyeongKi.
 */
class SampleCountViewModel(
    saveCurrentCountUseCase: SaveCurrentCountUseCase
) : ViewModel() {
    val machine = SampleCountUiMachine(coroutineScope = viewModelScope, saveCurrentCountUseCase)

    companion object {
        fun provideFactory(
            saveCurrentCountUseCase: SaveCurrentCountUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SampleCountViewModel(
                    saveCurrentCountUseCase
                ) as T
            }
        }
    }
}
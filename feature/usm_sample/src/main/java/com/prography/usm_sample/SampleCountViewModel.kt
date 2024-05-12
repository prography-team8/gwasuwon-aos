package com.prography.usm_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.usecase.LoadLastCountUseCase
import com.prography.domain.usecase.SaveCurrentCountUseCase

/**
 * Created by MyeongKi.
 */
class SampleCountViewModel(
    saveCurrentCountUseCase: SaveCurrentCountUseCase,
    loadLastCountUseCase: LoadLastCountUseCase,
) : ViewModel() {
    val machine = SampleCountUiMachine(coroutineScope = viewModelScope, saveCurrentCountUseCase, loadLastCountUseCase)

    companion object {
        fun provideFactory(
            saveCurrentCountUseCase: SaveCurrentCountUseCase,
            loadLastCountUseCase: LoadLastCountUseCase,

            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SampleCountViewModel(
                    saveCurrentCountUseCase,
                    loadLastCountUseCase
                ) as T
            }
        }
    }
}
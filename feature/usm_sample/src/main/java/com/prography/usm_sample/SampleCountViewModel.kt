package com.prography.usm_sample

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.usecase.LoadLastCountUseCase
import com.prography.domain.usecase.SaveCurrentCountUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class SampleCountViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    saveCurrentCountUseCase: SaveCurrentCountUseCase,
    loadLastCountUseCase: LoadLastCountUseCase,
) : ViewModel() {
    val machine = SampleCountUiMachine(
        viewModelScope,
        navigateFlow,
        saveCurrentCountUseCase,
        loadLastCountUseCase
    ).also {
        it.initMachine()
    }

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,

            saveCurrentCountUseCase: SaveCurrentCountUseCase,
            loadLastCountUseCase: LoadLastCountUseCase,

            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SampleCountViewModel(
                    navigateFlow,
                    saveCurrentCountUseCase,
                    loadLastCountUseCase
                ) as T
            }
        }
    }
}
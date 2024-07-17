package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.dialog.usecase.IsShowingNotifyLessonDeductedDialogUseCase
import com.prography.domain.dialog.usecase.UpdateShownNotifyLessonDeductedDialogUseCase
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.CertificateLessonUseCase
import com.prography.domain.lesson.usecase.CheckLessonByAttendanceUseCase
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import com.prography.domain.qr.CommonQrEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonDetailViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
    checkLessonByAttendanceUseCase: CheckLessonByAttendanceUseCase,
    isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
    updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
    certificateLessonUseCase: CertificateLessonUseCase

) : ViewModel() {
    val machine = LessonDetailUiMachine(
        lessonId = lessonId,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        commonQrFlow = commonQrFlow,
        commonLessonEvent = commonLessonEvent,
        loadLessonUseCase = loadLessonUseCase,
        loadLessonDatesUseCase = loadLessonDatesUseCase,
        deleteLessonUseCase = deleteLessonUseCase,
        checkLessonByAttendanceUseCase = checkLessonByAttendanceUseCase,
        isShowingNotifyLessonDeductedDialogUseCase = isShowingNotifyLessonDeductedDialogUseCase,
        updateShownNotifyLessonDeductedDialogUseCase = updateShownNotifyLessonDeductedDialogUseCase,
        certificateLessonUseCase = certificateLessonUseCase
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonQrFlow: MutableSharedFlow<CommonQrEvent>,
            commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
            loadLessonUseCase: LoadLessonUseCase,
            loadLessonDatesUseCase: LoadLessonDatesUseCase,
            deleteLessonUseCase: DeleteLessonUseCase,
            checkLessonByAttendanceUseCase: CheckLessonByAttendanceUseCase,
            isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
            updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
            certificateLessonUseCase: CertificateLessonUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonDetailViewModel(
                    lessonId,
                    navigateFlow,
                    commonLessonEvent,
                    commonQrFlow,
                    loadLessonUseCase,
                    loadLessonDatesUseCase,
                    deleteLessonUseCase,
                    checkLessonByAttendanceUseCase,
                    isShowingNotifyLessonDeductedDialogUseCase,
                    updateShownNotifyLessonDeductedDialogUseCase,
                    certificateLessonUseCase
                ) as T
            }
        }
    }
}
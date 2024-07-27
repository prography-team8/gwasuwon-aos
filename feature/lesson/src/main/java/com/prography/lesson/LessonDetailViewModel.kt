package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.dialog.usecase.IsShowingNotifyLessonDeductedDialogUseCase
import com.prography.domain.dialog.usecase.UpdateShownNotifyLessonDeductedDialogUseCase
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.UpdateAttendanceLessonUseCase
import com.prography.domain.lesson.usecase.UpdateForceAttendanceLessonUseCase
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonSchedulesUseCase
import com.prography.domain.preference.AccountPreference
import com.prography.domain.qr.CommonQrEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonDetailViewModel(
    lessonId: Long,
    isTeacher: Boolean,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    loadLessonSchedulesUseCase: LoadLessonSchedulesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
    updateForceAttendanceLessonUseCase: UpdateForceAttendanceLessonUseCase,
    isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
    updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
    updateAttendanceLessonUseCase: UpdateAttendanceLessonUseCase,
    accountPreference: AccountPreference
) : ViewModel() {
    val machine = LessonDetailUiMachine(
        lessonId = lessonId,
        isTeacher = isTeacher,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        commonQrFlow = commonQrFlow,
        commonLessonEvent = commonLessonEvent,
        loadLessonSchedulesUseCase = loadLessonSchedulesUseCase,
        deleteLessonUseCase = deleteLessonUseCase,
        updateForceAttendanceLessonUseCase = updateForceAttendanceLessonUseCase,
        isShowingNotifyLessonDeductedDialogUseCase = isShowingNotifyLessonDeductedDialogUseCase,
        updateShownNotifyLessonDeductedDialogUseCase = updateShownNotifyLessonDeductedDialogUseCase,
        updateAttendanceLessonUseCase = updateAttendanceLessonUseCase,
        accountPreference = accountPreference,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            isTeacher: Boolean,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonQrFlow: MutableSharedFlow<CommonQrEvent>,
            commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
            loadLessonSchedulesUseCase: LoadLessonSchedulesUseCase,
            deleteLessonUseCase: DeleteLessonUseCase,
            updateForceAttendanceLessonUseCase: UpdateForceAttendanceLessonUseCase,
            isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
            updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
            updateAttendanceLessonUseCase: UpdateAttendanceLessonUseCase,
            accountPreference: AccountPreference
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonDetailViewModel(
                    lessonId,
                    isTeacher,
                    navigateFlow,
                    commonLessonEvent,
                    commonQrFlow,
                    loadLessonSchedulesUseCase,
                    deleteLessonUseCase,
                    updateForceAttendanceLessonUseCase,
                    isShowingNotifyLessonDeductedDialogUseCase,
                    updateShownNotifyLessonDeductedDialogUseCase,
                    updateAttendanceLessonUseCase,
                    accountPreference
                ) as T
            }
        }
    }
}
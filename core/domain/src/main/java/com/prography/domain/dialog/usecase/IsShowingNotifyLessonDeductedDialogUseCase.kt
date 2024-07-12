package com.prography.domain.dialog.usecase

import com.prography.domain.dialog.DialogDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by MyeongKi.
 */
class IsShowingNotifyLessonDeductedDialogUseCase(
    private val dialogDataSource: DialogDataSource
) {
    operator fun invoke(lessonId: Long, currentLessonDeducted: Int): Flow<Boolean> {
        return dialogDataSource.getLastLessonDeductedDialogMeta(lessonId)
            .map { it.lessonDeducted < currentLessonDeducted }
    }
}
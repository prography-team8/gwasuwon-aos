package com.prography.domain.dialog.usecase

import com.prography.domain.dialog.DialogDataSource
import com.prography.domain.dialog.model.LessonDeductedDialogMeta
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class UpdateShownNotifyLessonDeductedDialogUseCase(
    private val dialogDataSource: DialogDataSource
) {
    operator fun invoke(lessonDeductedDialogMeta: LessonDeductedDialogMeta): Flow<Unit> {
        return dialogDataSource.saveLastLessonDeductedDialogMeta(lessonDeductedDialogMeta)
    }
}
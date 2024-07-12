package com.prography.domain.dialog

import com.prography.domain.dialog.model.LessonDeductedDialogMeta
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface DialogDataSource {
    fun getLastLessonDeductedDialogMeta(lessonId: Long): Flow<LessonDeductedDialogMeta>
    fun saveLastLessonDeductedDialogMeta(lessonDeductedDialogMeta: LessonDeductedDialogMeta): Flow<Unit>
}
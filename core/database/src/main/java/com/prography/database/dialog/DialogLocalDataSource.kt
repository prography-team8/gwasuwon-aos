package com.prography.database.dialog

import app.cash.sqldelight.db.SqlDriver
import com.prography.domain.dialog.DialogDataSource
import com.prography.domain.dialog.model.LessonDeductedDialogMeta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class DialogLocalDataSource(
    driver: SqlDriver
) : DialogDataSource {
    private val queries = GwasuwonDialogSqlDelightDatabase(driver).gwasuwonDialogSqlDelightDatabaseQueries
    override fun getLastLessonDeductedDialogMeta(lessonId: Long): Flow<LessonDeductedDialogMeta> {
        return flow {
            emit(
                queries.getLessonDeductedDialogMeta(lessonId).executeAsOneOrNull()
                    ?.toLessonDeductedDialogMeta() ?: LessonDeductedDialogMeta(lessonId, 0)
            )
        }
    }

    override fun saveLastLessonDeductedDialogMeta(lessonDeductedDialogMeta: LessonDeductedDialogMeta): Flow<Unit> {
        return flow {
            queries.isertLessonDeductedDialogMeta(
                lessonId = lessonDeductedDialogMeta.lessonId,
                lessonDeducted = lessonDeductedDialogMeta.lessonDeducted.toLong()
            )
            emit(Unit)
        }
    }

    fun LessonDeductedDialogMetaEntity.toLessonDeductedDialogMeta(): LessonDeductedDialogMeta {
        return LessonDeductedDialogMeta(
            lessonId = lessonId,
            lessonDeducted = lessonDeducted.toInt()
        )
    }
}

package com.prography.domain.lesson.respository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.prography.domain.lesson.LessonDataSource
import com.prography.domain.lesson.LoadLessonComposePagingSource
import com.prography.domain.lesson.PAGE_SIZE
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import com.prography.utils.date.toKtsTimeMillis
import com.prography.utils.date.toUtcTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by MyeongKi.
 */
class LessonRepositoryImpl(
    private val remoteSource: LessonDataSource
) : LessonRepository {
    override fun loadLessonContractUrl(lessonId: Long): Flow<String> {
        return remoteSource.loadLessonContractUrl(lessonId)
    }

    override fun loadLessons(): Flow<PagingData<Lesson>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                LoadLessonComposePagingSource(remoteSource)
            }
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.copy(
                        lessonStartDateTime = it.lessonStartDateTime.toKtsTimeMillis(),
                        lessonAttendanceDates = it.lessonAttendanceDates.map { date ->
                            date.toKtsTimeMillis()
                        },
                        lessonAbsentDates = it.lessonAbsentDates.map { date ->
                            date.toKtsTimeMillis()
                        }
                    )
                }
            }
    }

    override fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson> {
        return remoteSource.createLesson(
            requestOption.copy(
                lessonStartDateTime = requestOption.lessonStartDateTime.toUtcTimeMillis(),
            )
        )
    }

    override fun loadLesson(lessonId: Long): Flow<Lesson> {
        return remoteSource.loadLesson(lessonId).map {
            it.copy(
                lessonStartDateTime = it.lessonStartDateTime.toKtsTimeMillis(),
                lessonAttendanceDates = it.lessonAttendanceDates.map { date ->
                    date.toKtsTimeMillis()
                },
                lessonAbsentDates = it.lessonAbsentDates.map { date ->
                    date.toKtsTimeMillis()
                }
            )
        }
    }

    override fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson> {
        return remoteSource.updateLesson(
            requestOption.copy(
                lessonStartDateTime = requestOption.lessonStartDateTime.toUtcTimeMillis(),
            )
        )
    }

    override fun deleteLesson(lessonId: Long): Flow<Unit> {
        return remoteSource.deleteLesson(lessonId)
    }
}
package com.prography.domain.lesson.respository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prography.domain.lesson.LessonDataSource
import com.prography.domain.lesson.LoadLessonComposePagingSource
import com.prography.domain.lesson.PAGE_SIZE
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import com.prography.utils.date.toUtcTimeMillis
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LessonRepositoryImpl(
    private val remoteSource: LessonDataSource
) : LessonRepository {
    override fun loadLessonContractUrl(lessonId: Long): Flow<String> {
        return remoteSource.loadLessonContractUrl(lessonId)
    }

    override fun loadLessonCards(): Flow<PagingData<LessonCard>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                LoadLessonComposePagingSource(remoteSource)
            }
        ).flow
    }

    override fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson> {
        return remoteSource.createLesson(requestOption)
    }

    override fun loadLessonSchedules(lessonId: Long): Flow<LessonSchedules> {
        return remoteSource.loadLessonSchedules(lessonId)
    }

    override fun loadLesson(lessonId: Long): Flow<Lesson> {
        return remoteSource.loadLesson(lessonId)
    }

    override fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson> {
        return remoteSource.updateLesson(requestOption)
    }

    override fun deleteLesson(lessonId: Long): Flow<Unit> {
        return remoteSource.deleteLesson(lessonId)
    }

    override fun updateForceAttendanceLesson(scheduleId: Long): Flow<Long> {
        return remoteSource.updateForceAttendanceLesson(scheduleId)
    }

    override fun joinLesson(lessonId: Long): Flow<Long> {
        return remoteSource.joinLesson(lessonId)
    }

    override fun updateAttendanceLesson(lessonId: Long): Flow<Long> {
        return remoteSource.updateAttendanceLesson(lessonId)
    }
}
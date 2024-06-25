package com.prography.domain.lesson

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.LoadLessonsRequestOption
import kotlinx.coroutines.flow.last

/**
 * Created by MyeongKi.
 */
class LoadLessonComposePagingSource(
    private val remoteDataSource: LessonDataSource
):PagingSource<Int, Lesson>() {
    override fun getRefreshKey(state: PagingState<Int, Lesson>): Int {
        return START_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Lesson> {
        try {
            val currentPage = params.key ?: START_PAGE
            val result = remoteDataSource.loadLessons(
                requestOption = LoadLessonsRequestOption(
                    page = currentPage,
                    pageSize = PAGE_SIZE
                )
            ).last()
            return LoadResult.Page(
                data = result,
                prevKey = if (currentPage == START_PAGE) null else currentPage.dec(),
                nextKey = currentPage.inc()
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
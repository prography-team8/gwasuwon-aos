package com.prography.domain.lesson

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prography.domain.lesson.model.LessonCard
import kotlinx.coroutines.flow.last

/**
 * Created by MyeongKi.
 */
class LoadLessonComposePagingSource(
    private val remoteDataSource: LessonDataSource
):PagingSource<Int, LessonCard>() {
    override fun getRefreshKey(state: PagingState<Int, LessonCard>): Int {
        return START_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LessonCard> {
        try {
            val result = remoteDataSource.loadLessonCards().last()
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
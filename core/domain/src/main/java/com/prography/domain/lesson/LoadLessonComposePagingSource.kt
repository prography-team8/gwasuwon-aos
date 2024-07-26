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
            val currentPage = params.key ?: START_PAGE
            //FIXME server에서 페이징 미지원
            if(currentPage != START_PAGE){
                return LoadResult.Invalid()
            }
            val result = remoteDataSource.loadLessonCards().last()
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
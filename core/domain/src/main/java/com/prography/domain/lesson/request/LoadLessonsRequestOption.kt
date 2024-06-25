package com.prography.domain.lesson.request

import com.prography.domain.lesson.PAGE_SIZE
import com.prography.domain.lesson.START_PAGE

/**
 * Created by MyeongKi.
 */
data class LoadLessonsRequestOption(
    val page: Int = START_PAGE,
    val pageSize: Int = PAGE_SIZE,
)
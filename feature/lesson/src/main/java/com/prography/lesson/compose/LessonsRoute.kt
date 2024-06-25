package com.prography.lesson.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.lesson.LessonItemUiMachine
import com.prography.lesson.LessonsActionEvent
import com.prography.lesson.LessonsIntent
import com.prography.lesson.LessonsUiState
import com.prography.lesson.LessonsViewModel
import com.prography.ui.CommonToolbar
import com.prography.ui.GwasuwonTypography
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonsRoute(
    viewModel: LessonsViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    LessonsScreen(
        uiState = uiState,
        lessonsPagingFlow = viewModel.machine.lessonsPagingFlow,
        event = viewModel.machine.eventInvoker,
        intent = viewModel.machine.intentInvoker
    )
}

@Composable
private fun LessonsScreen(
    uiState: LessonsUiState,
    lessonsPagingFlow: Flow<PagingData<LessonItemUiMachine>>,
    event: (LessonsActionEvent) -> Unit,
    intent: (LessonsIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LessonsHeader()
        LessonsBody(
            uiState = uiState,
            lessonsPagingFlow = lessonsPagingFlow,
            event = event,
            intent = intent
        )
    }
}

@Composable
private fun LessonsHeader() {
    CommonToolbar(titleRes = R.string.lessons_title)
}

@Composable
private fun LessonsBody(
    uiState: LessonsUiState,
    lessonsPagingFlow: Flow<PagingData<LessonItemUiMachine>>,
    event: (LessonsActionEvent) -> Unit,
    intent: (LessonsIntent) -> Unit
) {
    val lessonItemPagingData = lessonsPagingFlow.collectAsLazyPagingItems()
    LaunchedEffect(uiState.isRequestRefresh) {
        if (uiState.isRequestRefresh) {
            lessonItemPagingData.refresh()
            event(LessonsActionEvent.OnStartRefresh)
        }
    }

    if (lessonItemPagingData.itemCount == 0) {
        LessonsEmpty(intent)
    } else {
        HasLessons(
            lessonItemPagingData,
            intent
        )
    }
}

@Composable
private fun LessonsEmpty(
    intent: (LessonsIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.lessons_empty_desc),
            style = GwasuwonTypography.Body1NormalRegular.textStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        EmptyCreateLessonButton {
            intent(LessonsIntent.ClickCreateLesson)
        }
    }
}

@Composable
private fun EmptyCreateLessonButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(GwasuwonConfigurationManager.colors.primaryNormal.toColor())
            .padding(
                vertical = dimensionResource(id = R.dimen.common_padding),
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.add_lesson),
            color = GwasuwonConfigurationManager.colors.staticWhite.toColor(),
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}

@Composable
private fun HasLessons(
    pagingItems: LazyPagingItems<LessonItemUiMachine>,
    intent: (LessonsIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        items(pagingItems.itemCount, key = { index ->
            pagingItems[index]?.uiState?.value?.lesson?.lessonId ?: -1
        }) { index ->
            pagingItems[index]?.let {
                LessonItemRoute(
                    machine = it
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            HasCreateLessonButton {
                intent(LessonsIntent.ClickCreateLesson)
            }
        }
    }
}

@Composable
private fun HasCreateLessonButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
            )
            .padding(
                vertical = dimensionResource(id = R.dimen.common_padding),
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_add),
            contentDescription = "add icon",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.common_icon_small_size)),
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            text = stringResource(id = R.string.add_lesson),
            color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}

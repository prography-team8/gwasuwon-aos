package com.prography.qr.compose

import androidx.compose.runtime.Composable
import com.prography.ui.R
import com.prography.ui.component.ErrorDialog

/**
 * Created by MyeongKi.
 */

@Composable
fun CertificateLessonErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.certificate_lesson_error_title,
        contentResId = R.string.certificate_lesson_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}

@Composable
fun JoinErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.join_lesson_error_title,
        contentResId = R.string.join_lesson_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}
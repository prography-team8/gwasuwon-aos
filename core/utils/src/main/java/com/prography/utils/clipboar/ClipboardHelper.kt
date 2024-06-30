package com.prography.utils.clipboar

import androidx.annotation.StringRes

/**
 * Created by MyeongKi.
 */
interface ClipboardHelper {
    fun copyToClipboard(copyText: String?, @StringRes resId: Int)
}
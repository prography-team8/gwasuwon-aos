package com.prography.utils.clipboar

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


/**
 * Created by MyeongKi.
 */
class ClipboardHelperImpl(private val context: Context) : ClipboardHelper {

    override fun copyToClipboard(copyText: String?) {
        val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(LABEL, copyText)
        clipboard.setPrimaryClip(clip)
    }

    companion object {
        private const val LABEL = "label"
    }
}
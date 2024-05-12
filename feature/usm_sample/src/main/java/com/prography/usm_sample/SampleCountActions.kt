package com.prography.usm_sample

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */

sealed interface SampleCountActionEvent : ActionEvent {
    data object AddCount : SampleCountActionEvent
    data object NavigateDetail : SampleCountActionEvent
    data object Refresh : SampleCountActionEvent
}

sealed interface SampleCountIntent : Intent<SampleCountActionEvent> {
    data object ClickAddBtn : SampleCountIntent
    data object ClickDetailBtn : SampleCountIntent

    override fun toActionEvent(): SampleCountActionEvent {
        return when (this) {
            is ClickAddBtn -> {
                SampleCountActionEvent.AddCount
            }

            is ClickDetailBtn -> {
                SampleCountActionEvent.NavigateDetail
            }
        }
    }
}
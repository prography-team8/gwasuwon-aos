package com.prography.usm_sample

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */

sealed interface SampleCountActionEvent : ActionEvent {
    data object AddCount : SampleCountActionEvent
}

sealed interface SampleCountIntent : Intent<SampleCountActionEvent> {
    data object ClickAddBtn : SampleCountIntent

    override fun toActionEvent(): SampleCountActionEvent {
        return when (this) {
            is ClickAddBtn -> {
                SampleCountActionEvent.AddCount
            }
        }
    }
}
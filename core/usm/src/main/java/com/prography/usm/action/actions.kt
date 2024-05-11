package com.prography.usm.action

/**
 * Created by MyeongKi.
 */
interface ActionEvent
interface Intent<T : ActionEvent> {
    fun toActionEvent(): T
}
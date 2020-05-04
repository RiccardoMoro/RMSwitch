package com.rm.switch.base

/**
 * @author Riccardo Moro.
 */
interface SwitchStateHandler<State : SwitchState> {
    fun getState(): State
    fun toggleState(): State
    fun moveToState(state: State): State
}

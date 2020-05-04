package com.rm.switch.twostates

import com.rm.switch.base.SwitchStateHandler

/**
 * @author Riccardo Moro.
 */
class TwoStatesSwitchHandler(
        initialState: TwoSwitchStates,
        private val loop: Boolean = true
) : SwitchStateHandler<TwoSwitchStates> {

    @Volatile
    private var state: TwoSwitchStates = initialState

    @Synchronized
    override fun getState(): TwoSwitchStates = state

    @Synchronized
    override fun toggleState(): TwoSwitchStates {
        if (loop || state == TwoSwitchStates.DISABLED) state++
        return state
    }

    @Synchronized
    override fun moveToState(state: TwoSwitchStates): TwoSwitchStates {
        this.state = state
        return state
    }
}
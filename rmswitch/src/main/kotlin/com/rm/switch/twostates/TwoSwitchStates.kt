package com.rm.switch.twostates

import com.rm.switch.base.SwitchState

/**
 * @author Riccardo Moro.
 */
enum class TwoSwitchStates : SwitchState {

    ENABLED, DISABLED;

    internal operator fun inc(): TwoSwitchStates = if (this == ENABLED) DISABLED else ENABLED
    internal operator fun dec(): TwoSwitchStates = this.inc()
}

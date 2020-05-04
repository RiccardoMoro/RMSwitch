package com.rm.switch.twostates

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * @author Riccardo Moro.
 */
class TwoSwitchStatesTest {

    @Test
    fun `should increment`() {
        // given
        var state: TwoSwitchStates = TwoSwitchStates.DISABLED

        // when
        state++

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.ENABLED)
    }

    @Test
    fun `should decrement`() {
        // given
        var state: TwoSwitchStates = TwoSwitchStates.ENABLED

        // when
        state--

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.DISABLED)
    }

    @Test
    fun `should loop`() {
        // given
        var state: TwoSwitchStates = TwoSwitchStates.ENABLED

        // when
        state++

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.DISABLED)
    }
}
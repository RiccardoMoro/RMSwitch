package com.rm.switch.twostates

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * @author Riccardo Moro
 */
class TwoStatesSwitchHandlerTest {

    @Test
    fun `should start in given state`() {
        // given
        val handler = TwoStatesSwitchHandler(
                initialState = TwoSwitchStates.DISABLED
        )

        // when

        // then
        assertThat(handler.getState()).isEqualTo(TwoSwitchStates.DISABLED)
    }

    @Test
    fun `should update state`() {
        // given
        val handler = TwoStatesSwitchHandler(
                initialState = TwoSwitchStates.DISABLED
        )

        // when
        handler.moveToState(TwoSwitchStates.ENABLED)

        // then
        assertThat(handler.getState()).isEqualTo(TwoSwitchStates.ENABLED)
    }

    @Test
    fun `should toggle`() {
        // given
        val handler = TwoStatesSwitchHandler(
                initialState = TwoSwitchStates.DISABLED
        )

        // when
        val state = handler.toggleState()

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.ENABLED)
    }

    @Test
    fun `should loop when enabled`() {
        // given
        val handler = TwoStatesSwitchHandler(
                initialState = TwoSwitchStates.ENABLED,
                loop = true
        )

        // when
        val state = handler.toggleState()

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.DISABLED)
    }

    @Test
    fun `should not loop when disabled`() {
        // given
        val handler = TwoStatesSwitchHandler(
                initialState = TwoSwitchStates.ENABLED,
                loop = false
        )

        // when
        val state = handler.toggleState()

        // then
        assertThat(state).isEqualTo(TwoSwitchStates.ENABLED)
    }
}
package com.rm.switch.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author Riccardo Moro.
 */
class RmSwitch @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val toggleImage = AppCompatImageView(context, attrs, defStyleAttr)

    init {

    }

    override fun onSaveInstanceState(): Parcelable? {
        return (super.onSaveInstanceState() as Bundle).apply {
        }
    }
}

package com.rm.rmswitch;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Riccardo on 31/08/16.
 */
public abstract class RMAbstractSwitch extends RelativeLayout
        implements Checkable, View.OnClickListener, TristateCheckable {

    // The possible toggle states
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_LEFT, STATE_MIDDLE, STATE_RIGHT})
    public @interface State {
    }

    public static final int STATE_LEFT = 0;
    public static final int STATE_MIDDLE = 1;
    public static final int STATE_RIGHT = 2;


    /**
     * If force aspect ratio or keep the given proportion
     */
    protected boolean mForceAspectRatio;

    /**
     * If the view is enabled
     */
    protected boolean mIsEnabled;


    /**
     * The Toggle view, the only moving part of the switch
     */
    protected SquareImageView mImgToggle;
    /**
     * The background image of the switch
     */
    protected ImageView mImgBkg;

    /**
     * The switch container Layout
     */
    protected RelativeLayout mContainerLayout;

    protected static final int ANIMATION_DURATION = 150;

    public RMAbstractSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Setup programmatically the appearance
    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
        setupSwitchAppearance();
    }

    public void setForceAspectRatio(boolean forceAspectRatio) {
        mForceAspectRatio = forceAspectRatio;
        setupSwitchAppearance();
    }

    // Get the switch setup
    public boolean isForceAspectRatio() {
        return mForceAspectRatio;
    }

    // Keep for compatibility, it was a damn typo!
    public boolean isForceAspectRation() {
        return isForceAspectRatio();
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // If set to wrap content, apply standard dimensions
        if (widthMode != MeasureSpec.EXACTLY) {
            int standardWith = (int) Utils
                    .convertDpToPixel(
                            getContext(),
                            getResources().getDimension(R.dimen.rm_switch_standard_width));

            // If unspecified or wrap_content where there's more space than the standard,
            // set the standard dimensions
            if ((widthMode == MeasureSpec.UNSPECIFIED) ||
                    (widthMode == MeasureSpec.AT_MOST &&
                            standardWith < MeasureSpec.getSize(widthMeasureSpec)))
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(standardWith, MeasureSpec.EXACTLY);
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            int standardHeight = (int) Utils
                    .convertDpToPixel(
                            getContext(),
                            getResources().getDimension(R.dimen.rm_switch_standard_height));

            // If unspecified or wrap_content where there's more space than the standard,
            // set the standard dimensions
            if ((heightMode == MeasureSpec.UNSPECIFIED) ||
                    (heightMode == MeasureSpec.AT_MOST &&
                            standardHeight < MeasureSpec.getSize(heightMeasureSpec)))
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(standardHeight, MeasureSpec
                        .EXACTLY);
        }

        // Fix the dimension depending on the aspect ratio forced or not
        if (mForceAspectRatio) {

            // Set the height depending on the width
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    (int) (MeasureSpec.getSize(widthMeasureSpec) / getSwitchAspectRatio()),
                    MeasureSpec.getMode(heightMeasureSpec));
        } else {

            // Check that the width is greater than the height, if not, resize and make a square
            if (MeasureSpec.getSize(widthMeasureSpec) < MeasureSpec.getSize(heightMeasureSpec))
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        MeasureSpec.getSize(widthMeasureSpec),
                        MeasureSpec.getMode(heightMeasureSpec));
        }

        // Set the margin after all measures have been done
        int calculatedMargin = MeasureSpec.getSize(heightMeasureSpec) > 0 ?
                MeasureSpec.getSize(heightMeasureSpec) / 8 :
                (int) Utils.convertDpToPixel(getContext(), 2);
        ((RelativeLayout.LayoutParams) mImgToggle.getLayoutParams()).setMargins(
                calculatedMargin, calculatedMargin, calculatedMargin, calculatedMargin);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void toggle() {

    }

    @Override
    public void setState(@RMTristateSwitch.State int state) {

    }

    @Override
    public int getState() {
        return RMTristateSwitch.STATE_LEFT;
    }

    @Override
    public void setChecked(boolean b) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    public abstract float getSwitchAspectRatio();

    public abstract void setupSwitchAppearance();
}

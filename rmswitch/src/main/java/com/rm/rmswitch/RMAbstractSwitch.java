package com.rm.rmswitch;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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

    protected static LayoutTransition sLayoutTransition;

    protected static final int ANIMATION_DURATION = 150;

    public RMAbstractSwitch(Context context) {
        this(context, null);
    }

    public RMAbstractSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RMAbstractSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Create the layout transition if not already created
        if (sLayoutTransition == null) {
            sLayoutTransition = new LayoutTransition();
            sLayoutTransition.setDuration(ANIMATION_DURATION);
            sLayoutTransition.enableTransitionType(LayoutTransition.CHANGING);
            sLayoutTransition.setInterpolator(
                    LayoutTransition.CHANGING,
                    new DecelerateInterpolator());
        }

        // Inflate the stock switch view
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.switch_view, this, true);

        // Get the sub-views
        mImgToggle = (SquareImageView) findViewById(R.id.rm_switch_view_toggle);
        mImgBkg = (ImageView) findViewById(R.id.rm_switch_view_bkgd);
        mContainerLayout = (RelativeLayout) findViewById(R.id.rm_switch_view_container);

        // Activate AnimateLayoutChanges in both the container and the root layout
        setLayoutTransition(sLayoutTransition);
        mContainerLayout.setLayoutTransition(sLayoutTransition);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                getTypedArrayResource(),
                defStyleAttr, 0);

        try {
            setupSwitchCustomAttributes(typedArray);
        } finally {
            typedArray.recycle();
        }

        // Set the OnClickListener
        setOnClickListener(this);
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


    // Layout rules management
    protected void removeRules(LayoutParams toggleParams, int[] rules) {
        for (int rule : rules) {
            removeRule(toggleParams, rule);
        }
    }

    protected void removeRule(LayoutParams toggleParams, int rule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            // RelativeLayout.LayoutParams.removeRule require api >= 17
            toggleParams.removeRule(rule);

        } else {

            // If API < 17 manually set the previously active rule with anchor 0 to remove it
            toggleParams.addRule(rule, 0);
        }
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

    // OnClick action
    @Override
    public void onClick(View view) {
        if (mIsEnabled)
            toggle();
    }

    public abstract float getSwitchAspectRatio();

    @StyleableRes
    public abstract int[] getTypedArrayResource();

    public abstract void setupSwitchAppearance();

    public abstract void setupSwitchCustomAttributes(TypedArray a);
}

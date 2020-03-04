package com.rm.rmswitch;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Riccardo on 31/08/16.
 */
@SuppressWarnings("ResourceType")
public abstract class RMAbstractSwitch extends RelativeLayout
        implements Checkable, View.OnClickListener, TristateCheckable, View.OnLayoutChangeListener {

    protected String TAG = getClass().getSimpleName();

    protected static final String BUNDLE_KEY_SUPER_DATA = "bundle_key_super_data";
    protected static final String BUNDLE_KEY_ENABLED = "bundle_key_enabled";
    protected static final String BUNDLE_KEY_FORCE_ASPECT_RATIO = "bundle_key_force_aspect_ratio";
    protected static final String BUNDLE_KEY_DESIGN = "bundle_key_design";

    protected static final float ALPHA_DISABLED = 0.6f;
    protected static final float ALPHA_ENABLED = 1.0f;

    // The possible toggle states
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_LEFT, STATE_MIDDLE, STATE_RIGHT})
    public @interface State {
    }

    // The possible switch designs
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DESIGN_LARGE, DESIGN_SLIM, DESIGN_ANDROID})
    public @interface SwitchDesign {
    }

    public static final int STATE_LEFT = 0;
    public static final int STATE_MIDDLE = 1;
    public static final int STATE_RIGHT = 2;

    public static final int DESIGN_LARGE = 0;
    public static final int DESIGN_SLIM = 1;
    public static final int DESIGN_ANDROID = 2;

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
     * The switch design
     */
    @SwitchDesign
    protected int mSwitchDesign;

    /**
     * The switch container Layout
     */
    protected RelativeLayout mContainerLayout;

    protected LayoutTransition mLayoutTransition;

    protected static final int ANIMATION_DURATION = 150;

    public RMAbstractSwitch(Context context) {
        this(context, null);
    }

    public RMAbstractSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RMAbstractSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public RMAbstractSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                getTypedArrayResource(),
                defStyleAttr,
                defStyleRes
        );

        mLayoutTransition = new LayoutTransition();
        mLayoutTransition.setDuration(ANIMATION_DURATION);
        mLayoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        mLayoutTransition.setInterpolator(
                LayoutTransition.CHANGING,
                new FastOutLinearInInterpolator()
        );

        // Check the switch style
        mSwitchDesign = typedArray.getInt(getSwitchDesignStyleable(), DESIGN_LARGE);
        if (mSwitchDesign == DESIGN_LARGE) {
            mSwitchDesign = typedArray
                    .getInt(getSwitchDesignStyleable(), DESIGN_LARGE);
        }

        setupLayout();

        try {
            setupSwitchCustomAttributes(typedArray);
        } finally {
            typedArray.recycle();
        }

        // Used to calculate margins and padding after layout changes
        addOnLayoutChangeListener(this);

        // Set the OnClickListener
        setOnClickListener(this);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_SUPER_DATA, super.onSaveInstanceState());
        bundle.putBoolean(BUNDLE_KEY_ENABLED, mIsEnabled);
        bundle.putBoolean(BUNDLE_KEY_FORCE_ASPECT_RATIO, mForceAspectRatio);
        bundle.putInt(BUNDLE_KEY_DESIGN, mSwitchDesign);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle prevState = (Bundle) state;

        super.onRestoreInstanceState(prevState.getParcelable(BUNDLE_KEY_SUPER_DATA));

        mIsEnabled = prevState.getBoolean(BUNDLE_KEY_ENABLED, true);
        mForceAspectRatio = prevState.getBoolean(BUNDLE_KEY_FORCE_ASPECT_RATIO, true);
        mSwitchDesign = prevState.getInt(BUNDLE_KEY_DESIGN, DESIGN_LARGE);
    }


    // Setters
    public void setEnabled(boolean enabled) {
        if (mIsEnabled != enabled) {
            mIsEnabled = enabled;
            setupSwitchAppearance();
        }
    }

    public void setForceAspectRatio(boolean forceAspectRatio) {
        if (forceAspectRatio != mForceAspectRatio) {
            mForceAspectRatio = forceAspectRatio;
            setupSwitchAppearance();
        }
    }

    public void setSwitchDesign(@SwitchDesign int switchDesign) {
        if (switchDesign != mSwitchDesign) {
            mSwitchDesign = switchDesign;
            setupLayout();
            setupSwitchAppearance();
        }

        // Used to calculate margins and padding after layout changes
        addOnLayoutChangeListener(this);
    }

    // Getters
    public boolean isForceAspectRatio() {
        return mForceAspectRatio;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    @SwitchDesign
    public int getSwitchDesign() {
        return mSwitchDesign;
    }


    // Get all the current views
    protected void setupLayout() {
        // Inflate the stock switch view
        removeAllViews();

        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(mSwitchDesign == DESIGN_SLIM ?
                                R.layout.switch_view_slim :
                                R.layout.switch_view,
                        this, true);

        // Get the sub-views
        mImgToggle = findViewById(R.id.rm_switch_view_toggle);
        mImgBkg = findViewById(R.id.rm_switch_view_bkg);
        mContainerLayout = findViewById(R.id.rm_switch_view_container);

        // Activate AnimateLayoutChanges in both the container and the root layout
        setLayoutTransition(mLayoutTransition);
        mContainerLayout.setLayoutTransition(mLayoutTransition);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // If set to wrap content, apply standard dimensions
        if (widthMode != MeasureSpec.EXACTLY) {
            int standardWith = getSwitchStandardWidth();

            // If unspecified or wrap_content where there's more space than the standard,
            // set the standard dimensions
            if ((widthMode == MeasureSpec.UNSPECIFIED) ||
                    (widthMode == MeasureSpec.AT_MOST &&
                            standardWith < MeasureSpec.getSize(widthMeasureSpec)))
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(standardWith, MeasureSpec.EXACTLY);
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            int standardHeight = getSwitchStandardHeight();

            // If unspecified or wrap_content where there's more space than the standard,
            // set the standard dimensions
            if ((heightMode == MeasureSpec.UNSPECIFIED) ||
                    (heightMode == MeasureSpec.AT_MOST &&
                            standardHeight < MeasureSpec.getSize(heightMeasureSpec)))
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(standardHeight,
                        MeasureSpec.EXACTLY);
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

        setBkgMargins(heightMeasureSpec, widthMeasureSpec);

        setToggleMargins(heightMeasureSpec);

        setToggleImagePadding();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setBkgMargins(int heightMeasureSpec, int widthMeasureSpec) {
        // If slim design add some margin to the background line, else remove them
        int calculatedBackgroundSideMargin = 0;
        int calculatedBackgroundTopBottomMargin = 0;

        if (mSwitchDesign == DESIGN_SLIM || mSwitchDesign == DESIGN_ANDROID) {
            calculatedBackgroundSideMargin = MeasureSpec.getSize(widthMeasureSpec) / 6;
        }

        if (mSwitchDesign == DESIGN_ANDROID) {
            calculatedBackgroundTopBottomMargin = MeasureSpec.getSize(heightMeasureSpec) / 6;
        }

        ((RelativeLayout.LayoutParams) mImgBkg.getLayoutParams()).setMargins(
                calculatedBackgroundSideMargin, calculatedBackgroundTopBottomMargin,
                calculatedBackgroundSideMargin, calculatedBackgroundTopBottomMargin);
    }

    private void setToggleMargins(int heightMeasureSpec) {
        // Set the margin after all measures have been done
        int calculatedToggleMargin;
        if (mSwitchDesign == DESIGN_LARGE) {
            calculatedToggleMargin = MeasureSpec.getSize(heightMeasureSpec) > 0 ?
                    MeasureSpec.getSize(heightMeasureSpec) / 6 :
                    (int) Utils.convertDpToPixel(getContext(), 2);
        } else {
            calculatedToggleMargin = 0;
        }

        ((RelativeLayout.LayoutParams) mImgToggle.getLayoutParams()).setMargins(
                calculatedToggleMargin, calculatedToggleMargin,
                calculatedToggleMargin, calculatedToggleMargin);
    }

    private void setToggleImagePadding() {
        // Set the padding of the image
        int padding;
        if (mSwitchDesign == DESIGN_LARGE) {
            padding = mImgToggle.getHeight() / 10;
        } else {
            padding = mImgToggle.getHeight() / 5;
        }
        mImgToggle.setPadding(padding, padding, padding, padding);
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

    protected void setSwitchAlpha() {
        setAlpha(mIsEnabled ? ALPHA_ENABLED : ALPHA_DISABLED);
    }

    protected void setupSwitchAppearance() {
        // Create the background drawables
        Drawable bkgDrawable = getSwitchCurrentBkgDrawable();

        // Create the toggle drawables
        Drawable toggleDrawable = getSwitchCurrentToggleDrawable();


        // Create the toggle background drawables
        Drawable toggleBkgDrawable = getSwitchCurrentToggleBkgDrawable();

        // Set the background drawable
        if (mImgBkg.getDrawable() != null) {
            // Create the transition for the background
            TransitionDrawable bkgTransitionDrawable = new TransitionDrawable(new Drawable[]{
                    // If it was a transition drawable, take the last one of it's drawables
                    mImgBkg.getDrawable() instanceof TransitionDrawable ?
                            ((TransitionDrawable) mImgBkg.getDrawable()).getDrawable(1) :
                            mImgBkg.getDrawable(),
                    bkgDrawable
            });
            bkgTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgBkg.setImageDrawable(bkgTransitionDrawable);
            bkgTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous background image, just set the new one
            mImgBkg.setImageDrawable(bkgDrawable);
        }

        // Set the toggle background
        if (mImgToggle.getBackground() != null) {
            // Create the transition for the background of the toggle
            TransitionDrawable toggleBkgTransitionDrawable =
                    new TransitionDrawable(new Drawable[]{
                            // If it was a transition drawable, take the last one of it's drawables
                            mImgToggle.getBackground() instanceof TransitionDrawable ?
                                    ((TransitionDrawable) mImgToggle.getBackground()).getDrawable
                                            (1) :
                                    mImgToggle.getBackground(),
                            toggleBkgDrawable
                    });
            toggleBkgTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgToggle.setBackground(toggleBkgTransitionDrawable);
            toggleBkgTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous background image, just set the new one
            mImgToggle.setImageDrawable(toggleBkgDrawable);
        }

        // Set the toggle image
        if (mImgToggle.getDrawable() != null) {
            // Create the transition for the image of the toggle
            TransitionDrawable toggleTransitionDrawable = new TransitionDrawable(new Drawable[]{
                    // If it was a transition drawable, take the last one of it's drawables
                    mImgToggle.getDrawable() instanceof TransitionDrawable ?
                            ((TransitionDrawable) mImgToggle.getDrawable()).getDrawable(1) :
                            mImgToggle.getDrawable(),
                    toggleDrawable
            });
            toggleTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgToggle.setImageDrawable(toggleTransitionDrawable);
            toggleTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous toggle image, just set the new one
            mImgToggle.setImageDrawable(toggleDrawable);
        }

        setSwitchAlpha();
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

    public abstract int getSwitchStandardWidth();

    public abstract int getSwitchStandardHeight();

    public abstract Drawable getSwitchCurrentToggleDrawable();

    public abstract Drawable getSwitchCurrentToggleBkgDrawable();

    public abstract Drawable getSwitchCurrentBkgDrawable();

    @StyleableRes
    public abstract int[] getTypedArrayResource();

    @StyleRes
    public abstract int getSwitchDesignStyleable();

    protected abstract void changeToggleGravity();

    protected abstract void setupSwitchCustomAttributes(TypedArray a);

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6,
                               int i7) {
        // Use this listener just when changing the switch layout
        removeOnLayoutChangeListener(this);

        // Change to the new margins and padding
        measure(getMeasuredWidth(), getMeasuredHeight());
    }
}

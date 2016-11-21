package com.rm.rmswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riccardo Moro on 18/08/2016.
 */
public class RMTristateSwitch extends RMAbstractSwitch {
    private static final String BUNDLE_KEY_STATE = "bundle_key_state";
    private static final String BUNDLE_KEY_RIGHT_TO_LEFT = "bundle_key_right_to_left";
    private static final String BUNDLE_KEY_BKG_LEFT_COLOR = "bundle_key_bkg_left_color";
    private static final String BUNDLE_KEY_BKG_MIDDLE_COLOR = "bundle_key_bkg_middle_color";
    private static final String BUNDLE_KEY_BKG_RIGHT_COLOR = "bundle_key_bkg_right_color";
    private static final String BUNDLE_KEY_TOGGLE_LEFT_COLOR = "bundle_key_toggle_left_color";
    private static final String BUNDLE_KEY_TOGGLE_MIDDLE_COLOR = "bundle_key_toggle_middle_color";
    private static final String BUNDLE_KEY_TOGGLE_RIGHT_COLOR = "bundle_key_toggle_right_color";
    private static final String BUNDLE_KEY_TOGGLE_LEFT_DRAWABLE =
            "bundle_key_toggle_left_drawable";
    private static final String BUNDLE_KEY_TOGGLE_MIDDLE_DRAWABLE =
            "bundle_key_toggle_middle_drawable";
    private static final String BUNDLE_KEY_TOGGLE_RIGHT_DRAWABLE =
            "bundle_key_toggle_right_drawable";

    private static final float SWITCH_STANDARD_ASPECT_RATIO = 2.6f;

    // View variables
    private List<RMTristateSwitchObserver> mObservers;

    /**
     * The current switch state
     */
    @State
    private int mCurrentState;

    /**
     * The direction of the selection on click
     */
    private boolean mRightToLeft;

    /**
     * The switch background color when is on the left
     */
    private int mBkgLeftColor;

    /**
     * The switch background color when is in the middle
     */
    private int mBkgMiddleColor;

    /**
     * The switch background color when is on the right
     */
    private int mBkgRightColor;

    /**
     * The toggle color when on the left
     */
    private int mToggleLeftColor;

    /**
     * The toggle color when in the middle
     */
    private int mToggleMiddleColor;

    /**
     * The toggle color when on the right
     */
    private int mToggleRightColor;

    /**
     * The toggle drawable when on the left
     */
    private Drawable mToggleLeftDrawable;

    /**
     * The toggle drawable when in the middle
     */
    private Drawable mToggleMiddleDrawable;

    /**
     * The toggle drawable when on the right
     */
    private Drawable mToggleRightDrawable;


    public RMTristateSwitch(Context context) {
        this(context, null);
    }

    public RMTristateSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RMTristateSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public float getSwitchAspectRatio() {
        return SWITCH_STANDARD_ASPECT_RATIO;
    }

    @Override
    public int getSwitchStandardWidth() {
        return getResources().getDimensionPixelSize(
                getSwitchDesign() != DESIGN_ANDROID ?
                        R.dimen.rm_triswitch_standard_width :
                        R.dimen.rm_triswitch_android_width);
    }

    @Override
    public int getSwitchStandardHeight() {
        return getResources().getDimensionPixelSize(
                getSwitchDesign() != DESIGN_ANDROID ?
                        R.dimen.rm_switch_standard_height :
                        R.dimen.rm_switch_android_height);
    }

    @Override
    public int[] getTypedArrayResource() {
        return R.styleable.RMTristateSwitch;
    }

    private void setMissingImages() {
        if (mToggleLeftDrawable != null)
            setMissingImagesFromDrawable(mToggleLeftDrawable);
        else if (mToggleMiddleDrawable != null)
            setMissingImagesFromDrawable(mToggleMiddleDrawable);
        else if (mToggleRightDrawable != null)
            setMissingImagesFromDrawable(mToggleRightDrawable);
    }

    // Add the missing images
    private void setMissingImagesFromDrawable(Drawable resource) {
        if (mToggleLeftDrawable == null)
            mToggleLeftDrawable = resource;
        if (mToggleMiddleDrawable == null)
            mToggleMiddleDrawable = resource;
        if (mToggleRightDrawable == null)
            mToggleRightDrawable = resource;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = (Bundle) super.onSaveInstanceState();

        bundle.putInt(BUNDLE_KEY_STATE, mCurrentState);

        bundle.putBoolean(BUNDLE_KEY_RIGHT_TO_LEFT, mRightToLeft);

        bundle.putInt(BUNDLE_KEY_BKG_LEFT_COLOR, mBkgLeftColor);
        bundle.putInt(BUNDLE_KEY_BKG_MIDDLE_COLOR, mBkgMiddleColor);
        bundle.putInt(BUNDLE_KEY_BKG_RIGHT_COLOR, mBkgRightColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_LEFT_COLOR, mToggleLeftColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_MIDDLE_COLOR, mToggleMiddleColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_RIGHT_COLOR, mToggleRightColor);

        return bundle;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Bundle prevState = (Bundle) state;

        // Restore the switch state notifying the observers
        mBkgLeftColor = prevState.getInt(BUNDLE_KEY_BKG_LEFT_COLOR,
                Utils.getDefaultBackgroundColor(getContext()));
        mBkgMiddleColor = prevState.getInt(BUNDLE_KEY_BKG_MIDDLE_COLOR,
                mBkgLeftColor);
        mBkgRightColor = prevState.getInt(BUNDLE_KEY_BKG_RIGHT_COLOR,
                mBkgLeftColor);

        mToggleLeftColor = prevState.getInt(BUNDLE_KEY_TOGGLE_LEFT_COLOR,
                Color.WHITE);
        mToggleMiddleColor = prevState.getInt(BUNDLE_KEY_TOGGLE_MIDDLE_COLOR,
                Utils.getPrimaryColor(getContext()));
        mToggleRightColor = prevState.getInt(BUNDLE_KEY_TOGGLE_RIGHT_COLOR,
                Utils.getAccentColor(getContext()));

        // Add all the missing images
        setMissingImages();

        //noinspection WrongConstant
        setState(prevState.getInt(BUNDLE_KEY_STATE, STATE_LEFT));
        notifyObservers();
    }


    // Setup programmatically the appearance
    public void setRightToLeft(boolean rightToLeft) {
        mRightToLeft = rightToLeft;
    }

    public void setSwitchBkgLeftColor(@ColorInt int color) {
        mBkgLeftColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchBkgMiddleColor(@ColorInt int color) {
        mBkgMiddleColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchBkgRightColor(@ColorInt int color) {
        mBkgRightColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleLeftColor(@ColorInt int color) {
        mToggleLeftColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleMiddleColor(@ColorInt int color) {
        mToggleMiddleColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleRightColor(@ColorInt int color) {
        mToggleRightColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleLeftDrawableRes(@DrawableRes int drawable) {
        setSwitchToggleLeftDrawable(drawable != 0 ?
                ContextCompat.getDrawable(getContext(), drawable) : null);
    }

    public void setSwitchToggleMiddleDrawableRes(@DrawableRes int drawable) {
        setSwitchToggleMiddleDrawable(drawable != 0 ?
                ContextCompat.getDrawable(getContext(), drawable) : null);
    }

    public void setSwitchToggleRightDrawableRes(@DrawableRes int drawable) {
        setSwitchToggleRightDrawable(drawable != 0 ?
                ContextCompat.getDrawable(getContext(), drawable) : null);
    }

    public void setSwitchToggleLeftDrawable(Drawable drawable) {
        mToggleLeftDrawable = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }

    public void setSwitchToggleMiddleDrawable(Drawable drawable) {
        mToggleMiddleDrawable = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }

    public void setSwitchToggleRightDrawable(Drawable drawable) {
        mToggleRightDrawable = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }


    // Get the switch setup
    public boolean isRightToLeft() {
        return mRightToLeft;
    }

    @ColorInt
    public int getSwitchBkgLeftColor() {
        return mBkgLeftColor;
    }

    @ColorInt
    public int getSwitchBkgMiddleColor() {
        return mBkgMiddleColor;
    }

    @ColorInt
    public int getSwitchBkgRightColor() {
        return mBkgRightColor;
    }

    @ColorInt
    public int getSwitchToggleLeftColor() {
        return mToggleLeftColor;
    }

    @ColorInt
    public int getSwitchToggleMiddleColor() {
        return mToggleMiddleColor;
    }

    @ColorInt
    public int getSwitchToggleRightColor() {
        return mToggleRightColor;
    }

    public Drawable getSwitchToggleLeftDrawable() {
        return mToggleLeftDrawable;
    }

    public Drawable getSwitchToggleMiddleDrawableRes() {
        return mToggleMiddleDrawable;
    }

    public Drawable getSwitchToggleRightDrawableRes() {
        return mToggleRightDrawable;
    }

    @Override
    public Drawable getSwitchCurrentToggleDrawable() {
        return mCurrentState == STATE_LEFT ?
                mToggleLeftDrawable :
                mCurrentState == STATE_MIDDLE ?
                        mToggleMiddleDrawable :
                        mToggleRightDrawable;
    }

    @Override
    public Drawable getSwitchCurrentToggleBkgDrawable() {
        Drawable toggleBkgDrawable = ContextCompat.getDrawable(getContext(),
                R.drawable.rounded_border_bkg);
        ((GradientDrawable) toggleBkgDrawable).setColor(mCurrentState == STATE_LEFT ?
                mToggleLeftColor :
                mCurrentState == STATE_MIDDLE ?
                        mToggleMiddleColor :
                        mToggleRightColor);

        return toggleBkgDrawable;
    }

    @Override
    public Drawable getSwitchCurrentBkgDrawable() {
        Drawable bkgDrawable = ContextCompat.getDrawable(getContext(),
                R.drawable.rounded_border_bkg);
        ((GradientDrawable) bkgDrawable).setColor(mCurrentState == STATE_LEFT ?
                mBkgLeftColor :
                mCurrentState == STATE_MIDDLE ?
                        mBkgMiddleColor :
                        mBkgRightColor);

        return bkgDrawable;
    }

    /**
     * Adds an observer to the list {@link #mObservers}
     *
     * @param switchObserver The observer to be added
     */
    public void addSwitchObserver(RMTristateSwitchObserver switchObserver) {
        if (switchObserver == null)
            return;

        if (mObservers == null)
            mObservers = new ArrayList<>();

        mObservers.add(switchObserver);
    }

    /**
     * Searches and removes the passed {@link RMTristateSwitchObserver}
     * from the observers list {@link #mObservers}
     *
     * @param switchObserver The observer to be removed
     */
    public void removeSwitchObserver(RMTristateSwitchObserver switchObserver) {
        if (switchObserver != null &&// Valid RMSwitchObserverPassed
                mObservers != null && mObservers.size() > 0 && // Observers list initialized and
                // not empty
                mObservers.indexOf(switchObserver) >= 0) {// new Observer found in the list
            mObservers.remove(mObservers.indexOf(switchObserver));
        }
    }

    /**
     * Notify all the registered observers
     */
    private void notifyObservers() {
        if (mObservers != null) {
            for (RMTristateSwitchObserver observer : mObservers) {
                observer.onCheckStateChange(this, mCurrentState);
            }
        }
    }

    /**
     * Removes all the observer from {@link #mObservers}
     */
    public void removeSwitchObservers() {
        if (mObservers != null && mObservers.size() > 0)
            mObservers.clear();
    }

    @Override
    public void setupSwitchCustomAttributes(TypedArray typedArray) {
        // Get the state
        //noinspection WrongConstant
        mCurrentState = typedArray.getInt(
                R.styleable.RMTristateSwitch_state, STATE_LEFT);

        // Keep aspect ratio flag
        mForceAspectRatio = typedArray.getBoolean(
                R.styleable.RMTristateSwitch_forceAspectRatio, true);

        // If the switch is enabled
        mIsEnabled = typedArray.getBoolean(
                R.styleable.RMTristateSwitch_enabled, true);

        // The direction of the selection
        mRightToLeft = typedArray.getBoolean(
                R.styleable.RMTristateSwitch_right_to_left, false);


        //Get the background color of the switch if left, middle or right
        mBkgLeftColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchBkgLeftColor,
                Utils.getDefaultBackgroundColor(getContext()));

        mBkgMiddleColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchBkgMiddleColor,
                mBkgLeftColor);

        mBkgRightColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchBkgRightColor,
                mBkgLeftColor);


        //Get the toggle color of the switch if left, middle or right
        mToggleLeftColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchToggleLeftColor,
                Color.WHITE);

        mToggleMiddleColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchToggleMiddleColor,
                Utils.getPrimaryColor(getContext()));

        mToggleRightColor = typedArray.getColor(
                R.styleable.RMTristateSwitch_switchToggleRightColor,
                Utils.getAccentColor(getContext()));


        // Get the toggle images when left, middle or right
        int toggleLeftDrawableResource = typedArray.getResourceId(
                R.styleable.RMTristateSwitch_switchToggleLeftImage, 0);
        int toggleMiddleDrawableResource = typedArray.getResourceId(
                R.styleable.RMTristateSwitch_switchToggleMiddleImage,
                toggleLeftDrawableResource);
        int toggleRightDrawableResource = typedArray.getResourceId(
                R.styleable.RMTristateSwitch_switchToggleRightImage,
                toggleLeftDrawableResource);

        if (toggleLeftDrawableResource != 0) {
            mToggleLeftDrawable = ContextCompat
                    .getDrawable(getContext(), toggleLeftDrawableResource);
        } else {
            mToggleLeftDrawable = null;
        }

        if (toggleMiddleDrawableResource != 0) {
            mToggleMiddleDrawable = ContextCompat
                    .getDrawable(getContext(), toggleMiddleDrawableResource);
        } else {
            mToggleMiddleDrawable = null;
        }

        if (toggleRightDrawableResource != 0) {
            mToggleRightDrawable = ContextCompat
                    .getDrawable(getContext(), toggleRightDrawableResource);
        } else {
            mToggleRightDrawable = null;
        }

        // If at least one image is set, add all the missing ones
        setMissingImages();

        // Update the appearance and change the toggle gravity
        setState(mCurrentState);
    }


    /**
     * Move the toggle from one state to the next, using the Gravity param
     * called AFTER setting the {@link #mCurrentState} variable
     */
    @Override
    protected void changeToggleGravity() {

        LayoutParams toggleParams =
                ((LayoutParams) mImgToggle.getLayoutParams());

        // Add the new alignment rule
        toggleParams.addRule(
                getCurrentLayoutRule());

        // Remove the previous alignment rule
        removePreviousRules(toggleParams);

        mImgToggle.setLayoutParams(toggleParams);
    }

    // Get the current layout rule to display the toggle in its correct position
    private int getCurrentLayoutRule() {
        return
                mCurrentState == STATE_LEFT ?
                        ALIGN_PARENT_LEFT :
                        mCurrentState == STATE_MIDDLE ?
                                CENTER_HORIZONTAL :
                                ALIGN_PARENT_RIGHT;
    }

    private void removePreviousRules(LayoutParams toggleParams) {
        if (mCurrentState == STATE_LEFT)
            removeRules(toggleParams, new int[]{CENTER_HORIZONTAL, ALIGN_PARENT_RIGHT});

        if (mCurrentState == STATE_MIDDLE)
            removeRules(toggleParams, new int[]{ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT});

        if (mCurrentState == STATE_RIGHT)
            removeRules(toggleParams, new int[]{ALIGN_PARENT_LEFT, CENTER_HORIZONTAL});
    }

    // Checkable interface methods

    /**
     * Change the state of the switch
     *
     * @param state The new state
     */
    @Override
    public void setState(@State int state) {
        mCurrentState = state;

        setupSwitchAppearance();
        changeToggleGravity();
    }

    /**
     * @return The current state
     */
    @Override
    @State
    public int getState() {
        return mCurrentState;
    }

    /**
     * Called to move the toggle to its next position
     */
    @Override
    public void toggle() {
        setState(getNextState());
        notifyObservers();
    }

    /**
     * @return The next state of the switch
     */
    @RMTristateSwitch.State
    private int getNextState() {
        if (!mRightToLeft) {
            if (mCurrentState == STATE_LEFT)
                return STATE_MIDDLE;
            if (mCurrentState == STATE_MIDDLE)
                return STATE_RIGHT;
            if (mCurrentState == STATE_RIGHT)
                return STATE_LEFT;
        } else {
            if (mCurrentState == STATE_RIGHT)
                return STATE_MIDDLE;
            if (mCurrentState == STATE_MIDDLE)
                return STATE_LEFT;
            if (mCurrentState == STATE_LEFT)
                return STATE_RIGHT;
        }
        return STATE_LEFT;
    }

    // Public interface to watch the switch state changes
    public interface RMTristateSwitchObserver {
        void onCheckStateChange(RMTristateSwitch switchView, @State int state);
    }
}

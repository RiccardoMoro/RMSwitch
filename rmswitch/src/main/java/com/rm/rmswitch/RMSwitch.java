package com.rm.rmswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
 * Created by Riccardo Moro on 29/07/2016.
 */
public class RMSwitch extends RMAbstractSwitch {
    private static final String BUNDLE_KEY_CHECKED = "bundle_key_checked";
    private static final String BUNDLE_KEY_BKG_CHECKED_COLOR = "bundle_key_bkg_checked_color";
    private static final String BUNDLE_KEY_BKG_NOT_CHECKED_COLOR =
            "bundle_key_bkg_not_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_CHECKED_COLOR = "bundle_key_toggle_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR =
            "bundle_key_toggle_not_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_CHECKED_DRAWABLE =
            "bundle_key_toggle_checked_drawable";
    private static final String BUNDLE_KEY_TOGGLE_NOT_CHECKED_DRAWABLE =
            "bundle_key_toggle_not_checked_drawable";

    private static final float SWITCH_STANDARD_ASPECT_RATIO = 2.2f;


    // View variables
    private List<RMSwitchObserver> mObservers;

    /**
     * The current switch state
     */
    private boolean mIsChecked;

    /**
     * The switch background color when is checked
     */
    private int mBkgCheckedColor;

    /**
     * The switch background color when is not checked
     */
    private int mBkgNotCheckedColor;

    /**
     * The toggle color when checked
     */
    private int mToggleCheckedColor;

    /**
     * The toggle color when not checked
     */
    private int mToggleNotCheckedColor;

    /**
     * The checked toggle drawable
     */
    private Drawable mToggleCheckedDrawable;

    /**
     * The not checked toggle drawable
     */
    private Drawable mToggleNotCheckedDrawable;


    public RMSwitch(Context context) {
        this(context, null);
    }

    public RMSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RMSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = (Bundle) super.onSaveInstanceState();

        bundle.putBoolean(BUNDLE_KEY_CHECKED, mIsChecked);

        bundle.putInt(BUNDLE_KEY_BKG_CHECKED_COLOR, mBkgCheckedColor);
        bundle.putInt(BUNDLE_KEY_BKG_NOT_CHECKED_COLOR, mBkgNotCheckedColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_CHECKED_COLOR, mToggleCheckedColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR, mToggleNotCheckedColor);

        return bundle;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Bundle prevState = (Bundle) state;

        // Restore the check state notifying the observers
        mBkgCheckedColor = prevState.getInt(BUNDLE_KEY_BKG_CHECKED_COLOR,
                Utils.getDefaultBackgroundColor(getContext()));
        mBkgNotCheckedColor = prevState.getInt(BUNDLE_KEY_BKG_NOT_CHECKED_COLOR,
                mBkgCheckedColor);

        mToggleCheckedColor = prevState.getInt(BUNDLE_KEY_TOGGLE_CHECKED_COLOR,
                Utils.getAccentColor(getContext()));
        mToggleNotCheckedColor = prevState.getInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR,
                Color.WHITE);

        setChecked(prevState.getBoolean(BUNDLE_KEY_CHECKED, false));
        notifyObservers();
    }


    // Setup programmatically the appearance

    public void setSwitchBkgCheckedColor(@ColorInt int color) {
        mBkgCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchBkgNotCheckedColor(@ColorInt int color) {
        mBkgNotCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleCheckedColor(@ColorInt int color) {
        mToggleCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleNotCheckedColor(@ColorInt int color) {
        mToggleNotCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleCheckedDrawableRes(@DrawableRes int drawable) {
        setSwitchToggleCheckedDrawable(drawable != 0 ?
                ContextCompat.getDrawable(getContext(), drawable) : null);
    }

    public void setSwitchToggleNotCheckedDrawableRes(@DrawableRes int drawable) {
        setSwitchToggleNotCheckedDrawable(drawable != 0 ?
                ContextCompat.getDrawable(getContext(), drawable) : null);
    }

    public void setSwitchToggleCheckedDrawable(Drawable drawable) {
        mToggleCheckedDrawable = drawable;
        setupSwitchAppearance();
    }

    public void setSwitchToggleNotCheckedDrawable(Drawable drawable) {
        mToggleNotCheckedDrawable = drawable;
        setupSwitchAppearance();
    }


    // Get the switch setup
    @ColorInt
    public int getSwitchBkgCheckedColor() {
        return mBkgCheckedColor;
    }

    @ColorInt
    public int getSwitchBkgNotCheckedColor() {
        return mBkgNotCheckedColor;
    }

    @ColorInt
    public int getSwitchToggleCheckedColor() {
        return mToggleCheckedColor;
    }

    @ColorInt
    public int getSwitchToggleNotCheckedColor() {
        return mToggleNotCheckedColor;
    }

    public Drawable getSwitchToggleCheckedDrawable() {
        return mToggleCheckedDrawable;
    }

    public Drawable getSwitchToggleNotCheckedDrawable() {
        return mToggleNotCheckedDrawable;
    }

    /**
     * Adds an observer to the list {@link #mObservers}
     *
     * @param switchObserver The observer to be added
     */
    public void addSwitchObserver(RMSwitchObserver switchObserver) {
        if (switchObserver == null)
            return;

        if (mObservers == null)
            mObservers = new ArrayList<>();

        mObservers.add(switchObserver);
    }

    /**
     * Searches and removes the passed {@link RMSwitchObserver}
     * from the observers list {@link #mObservers}
     *
     * @param switchObserver The observer to be removed
     */
    public void removeSwitchObserver(RMSwitchObserver switchObserver) {
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
            for (RMSwitchObserver observer : mObservers) {
                observer.onCheckStateChange(this, mIsChecked);
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
        // Get the checked flag
        mIsChecked = typedArray.getBoolean(
                R.styleable.RMSwitch_checked, false);

        // Keep aspect ratio flag
        mForceAspectRatio = typedArray.getBoolean(
                R.styleable.RMSwitch_forceAspectRatio, true);

        // If the switch is enabled
        mIsEnabled = typedArray.getBoolean(
                R.styleable.RMSwitch_enabled, true);


        //Get the background checked and not checked color
        mBkgCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchBkgCheckedColor,
                Utils.getDefaultBackgroundColor(getContext()));

        mBkgNotCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchBkgNotCheckedColor,
                mBkgCheckedColor);


        //Get the toggle checked and not checked colors
        mToggleCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchToggleCheckedColor,
                Utils.getAccentColor(getContext()));

        mToggleNotCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchToggleNotCheckedColor,
                Color.WHITE);


        // Get the toggle checked and not checked images
        int toggleCheckedDrawableResource = typedArray.getResourceId(
                R.styleable.RMSwitch_switchToggleCheckedImage, 0);
        int toggleNotCheckedDrawableResource = typedArray.getResourceId(
                R.styleable.RMSwitch_switchToggleNotCheckedImage,
                toggleCheckedDrawableResource);

        // If set the not checked drawable and not the checked one, copy the first
        if (toggleCheckedDrawableResource == 0 && toggleNotCheckedDrawableResource != 0)
            toggleCheckedDrawableResource = toggleNotCheckedDrawableResource;

        // Set the drawable from the drawable resource
        mToggleCheckedDrawable = toggleCheckedDrawableResource != 0 ?
                ContextCompat.getDrawable(getContext(), toggleCheckedDrawableResource) : null;
        mToggleNotCheckedDrawable = toggleNotCheckedDrawableResource != 0 ?
                ContextCompat.getDrawable(getContext(), toggleNotCheckedDrawableResource) : null;

        // Set manually checked flag, update the appearance and change the toggle gravity
        setChecked(mIsChecked);
    }

    /**
     * Move the toggle from one side to the other of this view,
     * called AFTER setting the {@link #mIsChecked} variable
     */
    @Override
    protected void changeToggleGravity() {

        LayoutParams toggleParams =
                ((LayoutParams) mImgToggle.getLayoutParams());

        // Add the new alignment rule
        toggleParams.addRule(
                getCurrentLayoutRule());

        // Remove the previous alignment rule
        removeRule(toggleParams, getPreviousLayoutRule());

        mImgToggle.setLayoutParams(toggleParams);
    }

    // Get the current layout rule to display the toggle in its correct position
    private int getCurrentLayoutRule() {
        return
                mIsChecked ?
                        ALIGN_PARENT_RIGHT :
                        ALIGN_PARENT_LEFT;
    }

    // Get the previous layout rule based on the current state and the toggle direction
    private int getPreviousLayoutRule() {
        return mIsChecked ?
                ALIGN_PARENT_LEFT :
                ALIGN_PARENT_RIGHT;
    }

    // Checkable interface methods
    @Override
    public void setChecked(boolean checked) {
        mIsChecked = checked;

        setupSwitchAppearance();
        changeToggleGravity();
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public float getSwitchAspectRatio() {
        return SWITCH_STANDARD_ASPECT_RATIO;
    }

    @Override
    public int getSwitchStandardWidth() {
        return getResources().getDimensionPixelSize(
                getSwitchDesign() == DESIGN_ANDROID ?
                        R.dimen.rm_switch_android_width : R.dimen.rm_switch_standard_width);
    }

    @Override
    public int getSwitchStandardHeight() {
        return getResources().getDimensionPixelSize(
                getSwitchDesign() == DESIGN_ANDROID ?
                        R.dimen.rm_switch_android_height : R.dimen.rm_switch_standard_height);
    }

    @Override
    public Drawable getSwitchCurrentToggleDrawable() {
        return mIsChecked ?
                // If checked
                mToggleCheckedDrawable
                // If not checked
                : mToggleNotCheckedDrawable;
    }

    @Override
    public Drawable getSwitchCurrentToggleBkgDrawable() {
        Drawable toggleBkgDrawable = ContextCompat.getDrawable(getContext(),
                R.drawable.rounded_border_bkg);
        ((GradientDrawable) toggleBkgDrawable).setColor(mIsChecked
                ? mToggleCheckedColor
                : mToggleNotCheckedColor);

        return toggleBkgDrawable;
    }

    @Override
    public Drawable getSwitchCurrentBkgDrawable() {
        Drawable bkgDrawable = ContextCompat.getDrawable(getContext(),
                R.drawable.rounded_border_bkg);
        ((GradientDrawable) bkgDrawable).setColor(
                mIsChecked
                        ? mBkgCheckedColor
                        : mBkgNotCheckedColor);
        return bkgDrawable;
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);
        notifyObservers();
    }

    // Public interface to watch the check state change
    public interface RMSwitchObserver {
        void onCheckStateChange(RMSwitch switchView, boolean isChecked);
    }

    @Override
    public int[] getTypedArrayResource() {
        return R.styleable.RMSwitch;
    }
}

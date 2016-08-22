package com.rm.rmswitch;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riccardo Moro on 18/08/2016.
 */
// TODO Update readme: all abount tristate
// TODO Add slim switch design
public class RMTristateSwitch extends RelativeLayout implements TristateCheckable, View.OnClickListener {
    private static final String BUNDLE_KEY_STATE = "bundle_key_state";
    private static final String BUNDLE_KEY_SUPER_DATA = "bundle_key_super_data";
    private static final String BUNDLE_KEY_ENABLED = "bundle_key_enabled";
    private static final String BUNDLE_KEY_FORCE_ASPECT_RATIO = "bundle_key_force_aspect_ratio";
    private static final String BUNDLE_KEY_RIGHT_TO_LEFT = "bundle_key_right_to_left";
    private static final String BUNDLE_KEY_BKG_LEFT_COLOR = "bundle_key_bkg_left_color";
    private static final String BUNDLE_KEY_BKG_MIDDLE_COLOR = "bundle_key_bkg_middle_color";
    private static final String BUNDLE_KEY_BKG_RIGHT_COLOR = "bundle_key_bkg_right_color";
    private static final String BUNDLE_KEY_TOGGLE_LEFT_COLOR = "bundle_key_toggle_left_color";
    private static final String BUNDLE_KEY_TOGGLE_MIDDLE_COLOR = "bundle_key_toggle_middle_color";
    private static final String BUNDLE_KEY_TOGGLE_RIGHT_COLOR = "bundle_key_toggle_right_color";
    private static final String BUNDLE_KEY_TOGGLE_LEFT_DRAWABLE_RES = "bundle_key_toggle_left_drawable_res";
    private static final String BUNDLE_KEY_TOGGLE_MIDDLE_DRAWABLE_RES = "bundle_key_toggle_middle_drawable_res";
    private static final String BUNDLE_KEY_TOGGLE_RIGHT_DRAWABLE_RES = "bundle_key_toggle_right_drawable_res";

    private static final int ANIMATION_DURATION = 150;
    private static final float SWITCH_STANDARD_ASPECT_RATIO = 2.6f;

    // The possible toggle states
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_LEFT, STATE_MIDDLE, STATE_RIGHT})
    public @interface State {
    }

    public static final int STATE_LEFT = 0;
    public static final int STATE_MIDDLE = 1;
    public static final int STATE_RIGHT = 2;

    /**
     * The Toggle view, the only moving part of the switch
     */
    private final SquareImageView mImgToggle;
    /**
     * The background image of the switch
     */
    private final ImageView mImgBkg;

    /**
     * The switch container Layout
     */
    private final RelativeLayout mContainerLayout;

    // View variables
    private List<RMTristateSwitchObserver> mObservers;

    /**
     * The current switch state
     */
    @State
    private int mCurrentState;


    /**
     * If force aspect ratio or keep the given proportion
     */
    private boolean mForceAspectRatio;

    /**
     * If the view is enabled
     */
    private boolean mIsEnabled;

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
     * The toggle drawable resource when on the left
     */
    private int mToggleLeftDrawableResource;

    /**
     * The toggle drawable resource when in the middle
     */
    private int mToggleMiddleDrawableResource;

    /**
     * The toggle drawable resource when on the right
     */
    private int mToggleRightDrawableResource;


    private static LayoutTransition sLayoutTransition;

    public RMTristateSwitch(Context context) {
        this(context, null);
    }

    public RMTristateSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RMTristateSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
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
                R.styleable.RMTristateSwitch,
                defStyleAttr, 0);

        try {
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
                    Utils.getDefaultBackgroundColor(context));

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
            mToggleLeftDrawableResource = typedArray.getResourceId(
                    R.styleable.RMTristateSwitch_switchToggleLeftImage, 0);
            mToggleMiddleDrawableResource = typedArray.getResourceId(
                    R.styleable.RMTristateSwitch_switchToggleMiddleImage, mToggleLeftDrawableResource);
            mToggleRightDrawableResource = typedArray.getResourceId(
                    R.styleable.RMTristateSwitch_switchToggleRightImage, mToggleLeftDrawableResource);

            // If at least one image is set, add all the missing ones
            setMissingImages();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
        // Set the OnClickListener
        setOnClickListener(this);

        // Update the appearance and change the toggle gravity
        setState(mCurrentState);
    }

    private void setMissingImages() {
        if (mToggleLeftDrawableResource != 0)
            setMissingImagesFromResource(mToggleLeftDrawableResource);
        else if (mToggleMiddleDrawableResource != 0)
            setMissingImagesFromResource(mToggleMiddleDrawableResource);
        else if (mToggleRightDrawableResource != 0)
            setMissingImagesFromResource(mToggleRightDrawableResource);
    }

    // Add the missing images
    private void setMissingImagesFromResource(@DrawableRes int resource) {
        if (mToggleLeftDrawableResource == 0)
            mToggleLeftDrawableResource = resource;
        if (mToggleMiddleDrawableResource == 0)
            mToggleMiddleDrawableResource = resource;
        if (mToggleRightDrawableResource == 0)
            mToggleRightDrawableResource = resource;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_SUPER_DATA, super.onSaveInstanceState());

        bundle.putInt(BUNDLE_KEY_STATE, mCurrentState);
        bundle.putBoolean(BUNDLE_KEY_ENABLED, mIsEnabled);
        bundle.putBoolean(BUNDLE_KEY_FORCE_ASPECT_RATIO, mForceAspectRatio);
        bundle.putBoolean(BUNDLE_KEY_RIGHT_TO_LEFT, mRightToLeft);

        bundle.putInt(BUNDLE_KEY_BKG_LEFT_COLOR, mBkgLeftColor);
        bundle.putInt(BUNDLE_KEY_BKG_MIDDLE_COLOR, mBkgMiddleColor);
        bundle.putInt(BUNDLE_KEY_BKG_RIGHT_COLOR, mBkgRightColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_LEFT_COLOR, mToggleLeftColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_MIDDLE_COLOR, mToggleMiddleColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_RIGHT_COLOR, mToggleRightColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_LEFT_DRAWABLE_RES, mToggleLeftDrawableResource);
        bundle.putInt(BUNDLE_KEY_TOGGLE_MIDDLE_DRAWABLE_RES, mToggleMiddleDrawableResource);
        bundle.putInt(BUNDLE_KEY_TOGGLE_RIGHT_DRAWABLE_RES, mToggleRightDrawableResource);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle prevState = (Bundle) state;

        super.onRestoreInstanceState(prevState.getParcelable(BUNDLE_KEY_SUPER_DATA));

        // Restore the check state notifying the observers
        mIsEnabled = prevState.getBoolean(BUNDLE_KEY_ENABLED, true);
        mForceAspectRatio = prevState.getBoolean(BUNDLE_KEY_FORCE_ASPECT_RATIO, true);

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

        mToggleLeftDrawableResource = prevState
                .getInt(BUNDLE_KEY_TOGGLE_LEFT_DRAWABLE_RES, 0);
        mToggleMiddleDrawableResource = prevState
                .getInt(BUNDLE_KEY_TOGGLE_MIDDLE_DRAWABLE_RES, 0);
        mToggleRightDrawableResource = prevState
                .getInt(BUNDLE_KEY_TOGGLE_RIGHT_DRAWABLE_RES, 0);

        // Add all the missing images
        setMissingImages();

        //noinspection WrongConstant
        setState(prevState.getInt(BUNDLE_KEY_STATE, STATE_LEFT));
        notifyObservers();
    }

    // TODO Fix forceAspectRatio
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mForceAspectRatio) {

            // Set the height depending on the width
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    (int) (MeasureSpec.getSize(widthMeasureSpec) / SWITCH_STANDARD_ASPECT_RATIO),
                    MeasureSpec.getMode(heightMeasureSpec));
        } else {

            // Check that the width is greater than the height, if not, resize and make a square
            if (MeasureSpec.getSize(widthMeasureSpec) < MeasureSpec.getSize(heightMeasureSpec))
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        MeasureSpec.getSize(widthMeasureSpec),
                        MeasureSpec.getMode(heightMeasureSpec));
        }

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
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(standardHeight, MeasureSpec.EXACTLY);
        }

        // Set the margin after all measures have been done
        int calculatedMargin = MeasureSpec.getSize(heightMeasureSpec) > 0 ?
                MeasureSpec.getSize(heightMeasureSpec) / 8 :
                (int) Utils.convertDpToPixel(getContext(), 2);
        ((RelativeLayout.LayoutParams) mImgToggle.getLayoutParams()).setMargins(
                calculatedMargin, calculatedMargin, calculatedMargin, calculatedMargin);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        mToggleLeftDrawableResource = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }

    public void setSwitchToggleMiddleDrawableRes(@DrawableRes int drawable) {
        mToggleMiddleDrawableResource = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }

    public void setSwitchToggleRightDrawableRes(@DrawableRes int drawable) {
        mToggleRightDrawableResource = drawable;
        setMissingImages();
        setupSwitchAppearance();
    }


    // Get the switch setup
    public boolean isForceAspectRation() {
        return mForceAspectRatio;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

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

    @DrawableRes
    public int getSwitchToggleLeftDrawableRes() {
        return mToggleLeftDrawableResource;
    }

    @DrawableRes
    public int getSwitchToggleMiddleDrawableRes() {
        return mToggleMiddleDrawableResource;
    }

    @DrawableRes
    public int getSwitchToggleRightDrawableRes() {
        return mToggleRightDrawableResource;
    }

    @ColorInt
    public int getSwitchCurrentBkgColor() {
        return getSwitchBkgColorForState(mCurrentState);
    }

    @ColorInt
    public int getSwitchToggleCurrentColor() {
        return getSwitchToggleColorForState(mCurrentState);
    }

    @DrawableRes
    public int getSwitchToggleCurrentDrawableResource() {
        return getSwitchToggleDrawableResourceForState(mCurrentState);
    }

    @ColorInt
    public int getSwitchBkgColorForState(@State int state) {
        return
                state == STATE_LEFT ?
                        mBkgLeftColor :
                        state == STATE_MIDDLE ?
                                mBkgMiddleColor :
                                mBkgRightColor;
    }

    @ColorInt
    public int getSwitchToggleColorForState(@State int state) {
        return
                state == STATE_LEFT ?
                        mToggleLeftColor :
                        state == STATE_MIDDLE ?
                                mToggleMiddleColor :
                                mToggleRightColor;
    }

    @DrawableRes
    public int getSwitchToggleDrawableResourceForState(@State int state) {
        return
                state == STATE_LEFT ?
                        mToggleLeftDrawableResource :
                        state == STATE_MIDDLE ?
                                mToggleMiddleDrawableResource :
                                mToggleRightDrawableResource;
    }

    @Nullable
    public Drawable getSwitchToggleCurrentDrawable() {
        try {
            int currentDrawableResource = getSwitchToggleCurrentDrawableResource();
            return
                    currentDrawableResource == 0 ?
                            null :
                            ContextCompat.getDrawable(getContext(), currentDrawableResource);
        } catch (Exception e) {
            return null;
        }
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
                mObservers != null && mObservers.size() > 0 && // Observers list initialized and not empty
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
                observer.onCheckStateChange(mCurrentState);
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

    /**
     * Setup all the switch custom attributes appearance
     */
    private void setupSwitchAppearance() {
        // Create the background drawables
        Drawable bkgDrawable =
                ContextCompat.getDrawable(getContext(), R.drawable.rounded_border_bkg);
        ((GradientDrawable) bkgDrawable).setColor(getSwitchCurrentBkgColor());

        // Create the toggle drawables
        Drawable toggleDrawable = getSwitchToggleCurrentDrawable();


        // Create the toggle background drawables
        Drawable toggleBkgDrawable =
                ContextCompat.getDrawable(getContext(), R.drawable.rounded_border_bkg);
        ((GradientDrawable) toggleBkgDrawable).setColor(getSwitchToggleCurrentColor());

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
                                    ((TransitionDrawable) mImgToggle.getBackground()).getDrawable(1) :
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

        setAlpha(mIsEnabled ? 1f : 0.6f);
    }


    /**
     * Move the toggle from one state to the next, using the Gravity param
     * called AFTER setting the {@link #mCurrentState} variable
     */
    private void changeToggleGravity() {

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

    private void removeRules(LayoutParams toggleParams, int[] rules) {
        for (int rule : rules) {
            removeRule(toggleParams, rule);
        }
    }

    private void removeRule(LayoutParams toggleParams, int rule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            // RelativeLayout.LayoutParams.removeRule require api >= 17
            toggleParams.removeRule(rule);

        } else {

            // If API < 17 manually set the previously active rule with anchor 0 to remove it
            toggleParams.addRule(rule, 0);
        }
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

    // OnClick action
    @Override
    public void onClick(View v) {
        if (isEnabled())
            toggle();
    }

    // Public interface to watch the check state change
    public interface RMTristateSwitchObserver {
        void onCheckStateChange(@State int state);
    }
}

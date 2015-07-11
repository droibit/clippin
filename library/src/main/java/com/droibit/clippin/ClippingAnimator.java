package com.droibit.clippin;

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Interface for applying the reveal eEffect on view.<br/>
 *
 * When the user does not call the {@link #target(View)} and {@link #circleCenter(int)} / {@link #circleCenter(View)},
 * it will throw an {@link IllegalStateException} at run time.
 *
 * @auther kumagai
 * @since 1.0
 */
public interface ClippingAnimator {

    /**
     * Set the view for the animation.
     *
     * @param targetView view for the animation
     */
    ClippingAnimator target(@NonNull View targetView);

    /**
     * Set the type of the center of circle.
     *
     * @param center type of the center of circle
     */
    ClippingAnimator circleCenter(@Clippin.Center int center);

    /**
     * set the view that becomes the center of the circle.<br/>
     * In calculating the center of the animation, using this property in preference.
     *
     * @param centerView Use the coordinates of the center of view
     */
    ClippingAnimator circleCenter(@NonNull View centerView);

    /**
     * Set the duration of the animation.<br/>
     * The default is 500 milliseconds.
     *
     * @param durationMillis duration milliseconds
     */
    ClippingAnimator duration(int durationMillis);

    /**
     * Set the delay time of the animation start.<br/>
     * By default, the delay is disabled.
     *
     * @param delayMillis delay milliseconds
     */
    ClippingAnimator startDelay(int delayMillis);

    /**
     * Set the interpolator for the animator.
     *
     * @param interpolator The TimeInterpolator to be used for animation
     */
    ClippingAnimator interpolator(@Nullable TimeInterpolator interpolator);

    /**
     * Start the animation circle spreads.
     *
     * @param callback called when the animation is finished
     */
    void show(@Nullable Clippin.Callback callback);

    /**
     * Start the animation circle narrows.
     * When the animation is finished, the visibility of this view is to become GONE.
     *
     * @param callback called when the animation is finished
     */
    void hide(@Nullable Clippin.Callback callback);
}

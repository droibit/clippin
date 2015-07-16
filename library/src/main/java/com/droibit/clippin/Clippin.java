package com.droibit.clippin;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.ViewAnimationUtils.createCircularReveal;

/**
 * Class for easily and flexibly applies the Reveal Effect" to the view.<br>
 * <br>
 * API 21 and more only apply the effect to the view. If it is less than API20,
 * {@link EmptyAnimator} is returned when you call. So, it is not necessary to be aware
 * of the version at runtime when used.<br>
 * <br>
 * When the animation is finished after {@link ClippingAnimator#hide(Callback)},
 * visibility of View will become GONE.<br>
 *
 * @auther kumagai
 * @since 1.0
 */
public final class Clippin {

    @IntDef({CENTER_NONE, CENTER_ORIGIN, CENTER_LEFT_TOP, CENTER_RIGHT_TOP, CENTER_LEFT_BOTTOM, CENTER_RIGHT_BOTTOM, CENTER_ORIGIN_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Center {}

    public static final int CENTER_ORIGIN = 0;
    public static final int CENTER_LEFT_TOP = 1;
    public static final int CENTER_RIGHT_TOP = 2;
    public static final int CENTER_LEFT_BOTTOM = 3;
    public static final int CENTER_RIGHT_BOTTOM = 4;
    public static final int CENTER_ORIGIN_BOTTOM = 5;

    public static final int DEFAULT_ANIMATION_MILLIS = 500;

    @VisibleForTesting
    static final int CENTER_NONE = -1;

    /**
     *　Call back when the animation is finished.
     */
    public interface Callback {

        /**
         * Notifies the end of the animation.
         */
        @UiThread
        void onAnimationEnd();
    }

    /**
     * This method returns a {@link ClippingAnimator} object, which can be used to animate
     * reveal effect to target view.
     *
     * @return Return the {@link ClippingAnimator} in accordance with the execution environment
     * @see LollipopAnimator
     * @see EmptyAnimator
     */
    public static ClippingAnimator animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new LollipopAnimator();
        }
        return new EmptyAnimator();
    }

    /**
     * Implementation class of {@link ClippingAnimator} to use and more API 21(5.0.x).
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static final class LollipopAnimator implements ClippingAnimator {

        View mTargetView;
        View mCircleCenterView;
        @Center
        int mCircleCenter;
        int mDuration;
        int mStartDelay;
        TimeInterpolator mInterpolator;

        LollipopAnimator() {
            mCircleCenter = CENTER_NONE;
            mDuration = DEFAULT_ANIMATION_MILLIS;
            mStartDelay = 0;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator target(@NonNull View targetView) {
            mTargetView = targetView;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator circleCenter(@Center int center) {
            mCircleCenter = center;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator circleCenter(@NonNull View centerView) {
            mCircleCenterView = centerView;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator duration(int durationMillis) {
            if (durationMillis > 0) {
                mDuration = durationMillis;
            }
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator startDelay(int delayMillis) {
            if (delayMillis > 0) {
                mStartDelay = delayMillis;
            }
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public ClippingAnimator interpolator(@Nullable TimeInterpolator interpolator) {
            mInterpolator = interpolator;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public void show(@Nullable final Callback callback) {
            validateNotNull();

            final Animator animator = makeAnimator(0, calculateCircleRadius());
            mTargetView.setVisibility(View.VISIBLE);

            if (callback != null) {
                animator.addListener(new AnimatorListener() {
                    @Override public void onAnimationEnd(@NonNull Animator animation) {
                        callback.onAnimationEnd();
                        animator.removeListener(this);
                    }
                });
            }
            animator.start();
        }

        /** {@inheritDoc} */
        @Override
        public void hide(@Nullable final Callback callback) {
            validateNotNull();

            final Animator animator = makeAnimator(calculateCircleRadius(), 0);
            animator.addListener(new AnimatorListener() {
                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    mTargetView.setVisibility(View.GONE);
                    if (callback != null) {
                        callback.onAnimationEnd();
                    }
                    animator.removeListener(this);
                }
            });
            animator.start();
        }

        @VisibleForTesting
        Animator makeAnimator(float startRadius, float endRadius) {
            final Point center = calculateCenterCoord();
            final Animator animator = createCircularReveal(mTargetView, center.x, center.y, startRadius, endRadius)
                                                .setDuration(mDuration);
            if (mStartDelay > 0) {
                animator.setStartDelay(mStartDelay);
            }
            if (mInterpolator != null) {
                animator.setInterpolator(mInterpolator);
            }
            return animator;
        }

        @VisibleForTesting
        Point calculateCenterCoord() {
            if (mCircleCenterView != null) {
                return MathUtils.calculateCenterCoord(mCircleCenterView, CENTER_ORIGIN);
            }
            return MathUtils.calculateCenterCoord(mTargetView, mCircleCenter);
        }

        @VisibleForTesting
        float calculateCircleRadius() {
            if (mCircleCenterView != null) {
                return MathUtils.calculateCircleRadius(mCircleCenterView, false);
            }

            final boolean useHypot = mCircleCenter == CENTER_LEFT_TOP    ||
                                     mCircleCenter == CENTER_RIGHT_TOP   ||
                                     mCircleCenter == CENTER_LEFT_BOTTOM ||
                                     mCircleCenter == CENTER_RIGHT_BOTTOM;
            return MathUtils.calculateCircleRadius(mTargetView, useHypot);
        }

        @VisibleForTesting
        void validateNotNull() {
            if (mTargetView == null) {
                throw new IllegalStateException("Calls the #target(View) method, you need to set the target view.");
            }
            if (mCircleCenterView == null && mCircleCenter == CENTER_NONE) {
                throw new IllegalStateException("Center coordinates of the circle animation is not set.");
            }
        }
    }

    /**
     * Empty {@link ClippingAnimator} to use and less API 19(4.4.x).<br>
     *
     * If {@link #show(Callback)} or {@link #hide(Callback)} method of argument of the callback is not null,
     * {@link Callback#onAnimationEnd()} will be called.
     */
    public static class EmptyAnimator implements ClippingAnimator {

        View mTargetView;

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator target(@NonNull View targetView) {
            mTargetView = targetView;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator circleCenter(@Center int from) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator circleCenter(@NonNull View centerView) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator duration(int durationMillis) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator startDelay(int delayMillis) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public ClippingAnimator interpolator(@Nullable TimeInterpolator interpolator) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public void show(@Nullable Callback callback) {
            validateNotNull();

            mTargetView.setVisibility(View.VISIBLE);
            callOnUI(callback);
        }

        /** {@inheritDoc} */
        @Override
        public void hide(@Nullable Callback callback) {
            validateNotNull();

            mTargetView.setVisibility(View.GONE);
            callOnUI(callback);
        }

        @VisibleForTesting
        void validateNotNull() {
            if (mTargetView == null) {
                throw new IllegalStateException("Calls the #target(View) method, you need to set the target view.");
            }
        }

        private void callOnUI(@Nullable final Callback callback) {
            if (callback == null) {
                return;
            }

            // If it is not the UI thread
            if (Looper.myLooper() != Looper.getMainLooper()) {
                mTargetView.post(new Runnable() {
                    @Override public void run() {
                        callback.onAnimationEnd();
                    }
                });
                return;
            }
            callback.onAnimationEnd();
        }
    }

    private static abstract class AnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}

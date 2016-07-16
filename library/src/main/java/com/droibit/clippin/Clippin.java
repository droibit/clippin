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

    public static final long DEFAULT_ANIMATION_MILLIS = 500L;

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

        View targetView;
        View circleCenterView;
        @Center
        int circleCenter;
        long duration;
        long startDelay;
        TimeInterpolator interpolator;

        LollipopAnimator() {
            circleCenter = CENTER_NONE;
            duration = DEFAULT_ANIMATION_MILLIS;
            startDelay = 0L;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator target(@NonNull View targetView) {
            this.targetView = targetView;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator circleCenter(@Center int center) {
            circleCenter = center;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator circleCenter(@NonNull View centerView) {
            circleCenterView = centerView;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator duration(long durationMillis) {
            if (durationMillis > 0L) {
                duration = durationMillis;
            }
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public LollipopAnimator startDelay(long delayMillis) {
            if (delayMillis > 0L) {
                startDelay = delayMillis;
            }
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public ClippingAnimator interpolator(@Nullable TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public void show(@Nullable final Callback callback) {
            validateNotNull();

            final Animator animator = makeAnimator(0, calculateCircleRadius());
            targetView.setVisibility(View.VISIBLE);

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
                    targetView.setVisibility(View.GONE);
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
            final Point center = calculateCenterCoordinate();
            final Animator animator = createCircularReveal(targetView, center.x, center.y, startRadius, endRadius)
                                                .setDuration(duration);
            if (startDelay > 0) {
                animator.setStartDelay(startDelay);
            }
            if (interpolator != null) {
                animator.setInterpolator(interpolator);
            }
            return animator;
        }

        @VisibleForTesting
        Point calculateCenterCoordinate() {
            if (circleCenterView != null) {
                return MathUtils.calculateCenterCoord(circleCenterView, CENTER_ORIGIN);
            }
            return MathUtils.calculateCenterCoord(targetView, circleCenter);
        }

        @VisibleForTesting
        float calculateCircleRadius() {
            // FIXME
            if (circleCenterView != null) {
                return MathUtils.calculateCircleRadius(targetView, false);
            }

            final boolean useHypot = circleCenter == CENTER_LEFT_TOP    ||
                                     circleCenter == CENTER_RIGHT_TOP   ||
                                     circleCenter == CENTER_LEFT_BOTTOM ||
                                     circleCenter == CENTER_RIGHT_BOTTOM;
            return MathUtils.calculateCircleRadius(targetView, useHypot);
        }

        @VisibleForTesting
        void validateNotNull() {
            if (targetView == null) {
                throw new IllegalStateException("Calls the #target(View) method, you need to set the target view.");
            }
            if (circleCenterView == null && circleCenter == CENTER_NONE) {
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

        View targetView;

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator target(@NonNull View targetView) {
            this.targetView = targetView;
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
        public EmptyAnimator duration(long durationMillis) {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public EmptyAnimator startDelay(long delayMillis) {
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

            targetView.setVisibility(View.VISIBLE);
            callOnUI(callback);
        }

        /** {@inheritDoc} */
        @Override
        public void hide(@Nullable Callback callback) {
            validateNotNull();

            targetView.setVisibility(View.GONE);
            callOnUI(callback);
        }

        @VisibleForTesting
        void validateNotNull() {
            if (targetView == null) {
                throw new IllegalStateException("Calls the #target(View) method, you need to set the target view.");
            }
        }

        private void callOnUI(@Nullable final Callback callback) {
            if (callback == null) {
                return;
            }

            // If it is not the UI thread
            if (Looper.myLooper() != Looper.getMainLooper()) {
                targetView.post(new Runnable() {
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

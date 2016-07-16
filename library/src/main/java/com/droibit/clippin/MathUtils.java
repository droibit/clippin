package com.droibit.clippin;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import static com.droibit.clippin.Clippin.CENTER_ORIGIN;
import static com.droibit.clippin.Clippin.CENTER_ORIGIN_BOTTOM;
import static com.droibit.clippin.Clippin.CENTER_LEFT_TOP;
import static com.droibit.clippin.Clippin.CENTER_LEFT_BOTTOM;
import static com.droibit.clippin.Clippin.CENTER_RIGHT_BOTTOM;
import static com.droibit.clippin.Clippin.CENTER_RIGHT_TOP;

/**
 * Class to calculate the numerical value for the ripple animation center View.
 *
 * @auther kumagai
 */
final class MathUtils {

    private MathUtils() {
    }

    /**
     * Calculate the radius of the animating circle center View.
     *
     * @param view view for the calculation
     * @param useHypot
     * @return radius of the animating circle
     */
    public static float calculateCircleRadius(View view, boolean useHypot) {
        if (useHypot) {
            return (float) Math.hypot(view.getWidth(), view.getHeight());
        }
        return Math.max(view.getWidth(), view.getHeight());
    }

    /**
     * Calculate the coordinates of the center of  the animating circle center View.
     *
     * @param view view for the calculation
     * @param center type of the center of circle
     * @return coordinate of the center of the animating circle
     */
    public static Point calculateCenterCoord(View view, @Clippin.Center int center) {
        final Rect viewRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        switch (center) {
            case CENTER_ORIGIN:
                return calculateOrigin(viewRect);
            case CENTER_ORIGIN_BOTTOM:
                return calculateOriginBottom(viewRect);
            case CENTER_LEFT_TOP:
                return calculateLeftTop(viewRect);
            case CENTER_RIGHT_TOP:
                return calculateRightTop(viewRect);
            case CENTER_LEFT_BOTTOM:
                return calculateLeftBottom(viewRect);
            case CENTER_RIGHT_BOTTOM:
                return calculateRightBottom(viewRect);
            default:
                throw new IllegalArgumentException("The value of the argument is the type of center that is not defined.");
        }
    }

    /**
     * Calculate the center of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateOrigin(Rect viewRect) {
        return new Point(viewRect.centerX(), viewRect.centerY());
    }

    /**
     * Calculate the center bottom of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateOriginBottom(Rect viewRect) {
        return new Point(viewRect.centerX(), viewRect.bottom);
    }

    /**
     * Calculate the left top of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateLeftTop(Rect viewRect) {
        return new Point(viewRect.left, viewRect.top);
    }

    /**
     * Calculate the right top of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateRightTop(Rect viewRect) {
        return new Point(viewRect.right, viewRect.top);
    }

    /**
     * Calculate the left bottom of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateLeftBottom(Rect viewRect) {
        return new Point(viewRect.left, viewRect.bottom);
    }

    /**
     * Calculate the right bottom of the view rectangle.
     */
    @VisibleForTesting
    static Point calculateRightBottom(Rect viewRect) {
        return new Point(viewRect.right, viewRect.bottom);
    }
}

package com.droibit.clippin;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.droibit.clippin.utils.Views.makeView;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Unit test of {@link Clippin.LollipopAnimator} class.
 *
 * @author kumagai
 * @since 1.0
 */
@RunWith(AndroidJUnit4.class)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LollipopAnimatorTest extends InstrumentationTestCase {
    
    private Context mContext;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mContext = getInstrumentation().getContext();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetTarget() throws Exception {
        final View view = makeView(mContext, 0, 0, 400, 500);
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();

        animator.target(view);
        assertNotNull(animator.mTargetView);
        assertThat(animator.mTargetView, is(view));

        // Force Error
        final View nullView = null;
        animator.target(nullView)
                .validateNotNull();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetCircleCenter() throws Exception {
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();

        animator.target(makeView(mContext, 0, 0, 572, 315))
                .circleCenter(Clippin.CENTER_ORIGIN);
        assertThat(animator.mCircleCenter, is(Clippin.CENTER_ORIGIN));

        // Calculate center coordinate
        final Point centerOrigin = animator.calculateCenterCoord();
        assertThat(centerOrigin, is(new Point(286, 157)));

        // Change circle center and calculate center coordinate
        animator.circleCenter(Clippin.CENTER_RIGHT_TOP);
        assertThat(animator.mCircleCenter, is(Clippin.CENTER_RIGHT_TOP));
        final Point centerRightTop = animator.calculateCenterCoord();
        assertThat(centerRightTop, is(new Point(572, 0)));

        // calculate circle radius (center is right top)
        final float radius = animator.calculateCircleRadius();
        assertThat(radius, is(653f));

        // Force Error
        animator.circleCenter(Clippin.CENTER_NONE)
                .validateNotNull();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetCircleCenterView() throws Exception {
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();
        animator.target(makeView(mContext, 0, 0, 200, 200));

        final View centerView = makeView(mContext, 0, 0, 100, 100);
        animator.circleCenter(centerView);
        assertNotNull(animator.mCircleCenterView);
        assertThat(animator.mCircleCenterView, is(centerView));

        // Calculate center coordinate
        final Point center1 = animator.calculateCenterCoord();
        assertThat(center1, is(new Point(50, 50)));
        // Calculate circle radius
        final float radius1 = animator.calculateCircleRadius();
        assertThat(radius1, is(200f));

        // Prefer view (circle center and radius)
        animator.circleCenter(Clippin.CENTER_LEFT_TOP);
        final Point center2 = animator.calculateCenterCoord();
        assertThat(center2, is(new Point(50, 50)));
        final float radius2 = animator.calculateCircleRadius();
        assertThat(radius2, is(200f));

        // Force Error
        final View nullView = null;
        animator.circleCenter(nullView)
                .circleCenter(Clippin.CENTER_NONE)
                .validateNotNull();
    }

    @Test
    public void testSetDuration() throws Exception {
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();

        animator.duration(1000);
        assertThat(animator.mDuration, is(1000));

        // Less than 0 is not accepted
        final Clippin.LollipopAnimator defaultAnimator = (Clippin.LollipopAnimator) Clippin.animate();
        defaultAnimator.duration(-1);
        assertThat(defaultAnimator.mDuration, not(-1));
        assertThat(defaultAnimator.mDuration, is(Clippin.DEFAULT_ANIMATION_MILLIS));
    }

    @Test
    public void testSetStartDelay() throws Exception {
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();

        animator.startDelay(50);
        assertThat(animator.mStartDelay, is(50));

        // Less than 0 is not accepted
        final Clippin.LollipopAnimator defaultAnimator = (Clippin.LollipopAnimator) Clippin.animate();
        defaultAnimator.startDelay(-1);
        assertThat(defaultAnimator.mStartDelay, not(-1));
        assertThat(defaultAnimator.mStartDelay, is(0));
    }

    @Test
    public void testSetInterpolator() throws Exception {
        final Clippin.LollipopAnimator animator = (Clippin.LollipopAnimator) Clippin.animate();

        animator.interpolator(new AccelerateDecelerateInterpolator());
        assertThat(animator.mInterpolator, instanceOf(AccelerateDecelerateInterpolator.class));

        // Allow null
        animator.interpolator(null);
        assertNull(animator.mInterpolator);
    }

    @Test
    public void testAll() {
        final View targetView = makeView(mContext, 0, 0, 500, 500);
        final Clippin.LollipopAnimator animator = ((Clippin.LollipopAnimator) Clippin.animate());
        animator.target(targetView)
                .circleCenter(Clippin.CENTER_ORIGIN)
                .duration(250)
                .startDelay(10)
                .interpolator(new AccelerateInterpolator());

        assertThat(animator.mTargetView, is(targetView));
        assertThat(animator.mDuration, is(250));
        assertThat(animator.mStartDelay, is(10));
        assertThat(animator.mInterpolator, instanceOf(AccelerateInterpolator.class));

        animator.validateNotNull();
        final Point center = animator.calculateCenterCoord();
        assertThat(center, is(new Point(250, 250)));
    }
}
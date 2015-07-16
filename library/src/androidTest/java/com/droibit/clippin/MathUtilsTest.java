package com.droibit.clippin;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.droibit.clippin.utils.Views.makeView;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test of {@link MathUtils} class.
 *
 * @author kumagai
 * @since 1.0
 */
@RunWith(AndroidJUnit4.class)
public class MathUtilsTest extends InstrumentationTestCase {

    private Context mContext;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mContext = getInstrumentation().getContext();
    }

    // Calc view center
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateCenterCoord() {
        // Origin
        final View view1 = makeView(mContext, 0, 0, 100, 200);
        final Point origin = MathUtils.calculateCenterCoord(view1, Clippin.CENTER_ORIGIN);
        assertThat(origin, is(new Point(50, 100)));

        // Left top
        final View view2 = makeView(mContext, 0, 0, 200, 100);
        final Point leftTop = MathUtils.calculateCenterCoord(view2, Clippin.CENTER_LEFT_TOP);
        assertThat(leftTop, is(new Point(0, 0)));

        // Left top
        final View view3 = makeView(mContext, 0, 0, 200, 100);
        final Point rightBottom = MathUtils.calculateCenterCoord(view3, Clippin.CENTER_RIGHT_BOTTOM);
        assertThat(rightBottom, is(new Point(200, 100)));

        // Force Error!!
        MathUtils.calculateCenterCoord(view3, Clippin.CENTER_NONE);
    }

    // Calc view radius
    @Test
    public void testCalculateCircleRadiusDoNotUseHypot() throws Exception {
        final View longHeightView = makeView(mContext, 0, 0, 100, 200);
        final float hRadius = MathUtils.calculateCircleRadius(longHeightView, false);
        assertThat(hRadius, is(200f));

        final View longWidthView = makeView(mContext, 0, 0, 300, 100);
        final float wRadius = MathUtils.calculateCircleRadius(longWidthView, false);
        assertThat(wRadius, is(300f));
    }

    @Test
    public void testCalculateCircleRadiusUseHypot() {
        final View longHeightView = makeView(mContext, 0, 0, 9, 40);
        final float hRadius = MathUtils.calculateCircleRadius(longHeightView, true);
        assertThat(hRadius, is(41f));

        final View longWidthView = makeView(mContext, 0, 0, 72, 65);
        final float wRadius = MathUtils.calculateCircleRadius(longWidthView, true);
        assertThat(wRadius, is(97f));
    }

    // Calc origin
    @Test
    public void testCalculateOrigin() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateOrigin(viewRect);
        assertThat(point, is(new Point(50, 50)));
    }

    // Calc origin bottom
    @Test
    public void testCalculateOriginBottom() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateOriginBottom(viewRect);
        assertThat(point, is(new Point(50, 100)));
    }

    // Calc left top
    @Test
    public void testCalculateLeftTop() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateLeftTop(viewRect);
        assertThat(point, is(new Point(0, 0)));
    }

    // Calc right top
    @Test
    public void testCalculateRightTop() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateRightTop(viewRect);
        assertThat(point, is(new Point(100, 0)));
    }

    // Calc left bottom
    @Test
    public void testCalculateLeftBottom() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateLeftBottom(viewRect);
        assertThat(point, is(new Point(0, 100)));
    }

    // Calc right bottom
    @Test
    public void testCalculateRightBottom() {
        final Rect viewRect = new Rect(0, 0, 100, 100);
        final Point point = MathUtils.calculateRightBottom(viewRect);
        assertThat(point, is(new Point(100, 100)));
    }
}
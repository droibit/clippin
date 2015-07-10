package com.droibit.app;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.droibit.app.utils.Views.*;
import static com.droibit.app.utils.Views.makeView;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Unit test of {@link MathUtils} class.
 *
 * @author kumagai
 * @since 2015/07/10.
 */
@RunWith(AndroidJUnit4.class)
public class MathUtilsTest {

    // Calc view center
    @Test
    public void testCalculateCenterCoord() {

    }

    // Calc view radius
    @Test
    public void testCalculateViewRadius() throws Exception {
        final View longHeightView = makeView(0, 0, 100, 200);
        final int hRadius = MathUtils.calculateViewRadius(longHeightView);
        assertThat(hRadius, is(200));

        final View longWidthView = makeView(0, 0, 300, 100);
        final int wRadius = MathUtils.calculateViewRadius(longWidthView);
        assertThat(wRadius, is(300));
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
        final View view = makeView(0, 0, 100, 100);
        final Point point = MathUtils.calculateOriginBottom(makeViewRect(view));
        assertThat(point, is(new Point(0, 100)));
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
        final Point point = MathUtils.calculateLeftTop(viewRect);
        assertThat(point, is(new Point(100, 100)));
    }
}
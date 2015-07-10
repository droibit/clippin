package com.droibit.app;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test of {@link Clippin.EmptyAnimator} class.
 *
 * @author kumagai
 * @since 1.0
 */
public class EmptyAnimatorTest extends AndroidTestCase {

    @Test
    public void testShow() throws Exception {
        final Clippin.EmptyAnimator animator = new Clippin.EmptyAnimator();
        animator.show(new Clippin.Callback() {
            @Override public void onAnimationEnd() {
                assertTrue(true);
            }
        });
    }

    @Test
    public void testHide() throws Exception {
        final Clippin.EmptyAnimator animator = new Clippin.EmptyAnimator();
        animator.hide(new Clippin.Callback() {
            @Override public void onAnimationEnd() {
                assertTrue(true);
            }
        });
    }
}
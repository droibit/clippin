package com.droibit.clippin;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.droibit.clippin.utils.Views.makeView;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Unit test of {@link Clippin.EmptyAnimator} class.
 *
 * @author kumagai
 */
@RunWith(AndroidJUnit4.class)
public class EmptyAnimatorTest extends InstrumentationTestCase {

    private Context mContext;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mContext = getInstrumentation().getContext();
    }

    @Test(expected = IllegalStateException.class)
    public void testShow() throws Exception {
        final Clippin.EmptyAnimator animator = new Clippin.EmptyAnimator()
                                                          .target(makeView(mContext, 0, 0, 100, 100));
        animator.show(new Clippin.Callback() {
            @Override public void onAnimationEnd() {
                assertTrue(true);
            }
        });
        assertThat(animator.targetView.getVisibility(), is(View.VISIBLE));

        // Force Error
        final View nullView = null;
        animator.target(nullView)
                .show(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testHide() throws Exception {
        final Clippin.EmptyAnimator animator = new Clippin.EmptyAnimator()
                                                          .target(makeView(mContext, 0, 0, 100, 100));
        animator.hide(new Clippin.Callback() {
            @Override public void onAnimationEnd() {
                assertTrue(true);
            }
        });
        assertThat(animator.targetView.getVisibility(), is(View.GONE));

        // Force Error
        final View nullView = null;
        animator.target(nullView)
                .hide(null);
    }
}
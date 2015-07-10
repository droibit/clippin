package com.droibit.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Unit test of {@link Clippin} class.
 *
 * @author kumagai
 * @since 1.0
 */
@RunWith(AndroidJUnit4.class)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ClippinTest extends InstantiationException {

    @Test
    public void testAnimate() throws Exception {
        final ClippingAnimator animator = Clippin.animate();
        assertNotNull(animator);
        assertThat(animator, instanceOf(Clippin.LollipopAnimator.class));
    }
}
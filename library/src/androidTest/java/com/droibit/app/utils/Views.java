package com.droibit.app.utils;

import android.graphics.Rect;
import android.view.View;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by kumagai on 2015/07/10.
 */
public final class Views {

    public static View makeView(int left, int top, int right, int bottom) {
        final View view = new View(RuntimeEnvironment.application);
        view.setLeft(left);
        view.setTop(top);
        view.setRight(right);
        view.setBottom(bottom);
        return view;
    }

    public static Rect makeViewRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
}

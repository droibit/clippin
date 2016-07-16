package com.droibit.clippin.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;


/**
 * @author kumagai
 */
public final class Views {

    public static View makeView(Context context, int left, int top, int right, int bottom) {
        final View view = new View(context);
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

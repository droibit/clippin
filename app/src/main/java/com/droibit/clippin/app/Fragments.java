package com.droibit.clippin.app;

import android.graphics.Color;
import android.support.v4.util.Pair;

import com.droibit.app.Clippin;

import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @auther kumagai
 * @since 1.0
 */
public final class Fragments {

    public static List<Pair<String, Integer>> sCenters = Arrays.asList(
            new Pair<>("Left Top", Clippin.CENTER_LEFT_TOP),
            new Pair<>("Right Top", Clippin.CENTER_RIGHT_TOP),
            new Pair<>("Left Bottom", Clippin.CENTER_LEFT_BOTTOM),
            new Pair<>("Right Bottom", Clippin.CENTER_RIGHT_BOTTOM),
            new Pair<>("Origin", Clippin.CENTER_ORIGIN),
            new Pair<>("Origin Bottom", Clippin.CENTER_ORIGIN_BOTTOM),
            new Pair<>("View", 6)
    );

    public static  List<Integer> sColors = Arrays.asList(
            Color.CYAN,
            Color.BLUE,
            Color.DKGRAY,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.YELLOW,
            Color.LTGRAY
    );
}

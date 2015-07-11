package com.droibit.clippin.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.droibit.clippin.Clippin;
import com.droibit.clippin.ClippingAnimator;

/**
 * @auther kumagai
 * @since 1.0
 */
public class EffectableFragment extends Fragment {

    private View mOverlay;

    private static final String ARG_PAGE_INDEX = "index";
    private int mCenterType;
    private int mColor;


    public static EffectableFragment newInstance(int index) {
        final Bundle args = new Bundle(1);
        args.putInt(ARG_PAGE_INDEX, index);

        final EffectableFragment f = new EffectableFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int index = getArguments().getInt(ARG_PAGE_INDEX);
        final Pair<String, Integer> center = Fragments.sCenters.get(index);
        mCenterType = center.second;
        mColor = Fragments.sColors.get(index);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_effect, container, false);
    }

    /** {@inheritDoc} */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOverlay = view.findViewById(R.id.overlay);
        mOverlay.setBackgroundColor(mColor);
    }

    public void onClick(View v) {
        final ClippingAnimator animator = Clippin.animate()
                                                 .target(mOverlay)
                                                 .duration(400);
        if (mCenterType <= Clippin.CENTER_RIGHT_BOTTOM) {
            animator.circleCenter(mCenterType);
        } else {
            animator.circleCenter(v);
        }

        if (mOverlay.getVisibility() != View.VISIBLE) {
            animator.show(null);
            return;
        }
        animator.hide(new Clippin.Callback() {
            @Override public void onAnimationEnd() {
                if (mCenterType == Clippin.CENTER_LEFT_TOP) {
                    Toast.makeText(getActivity(), "Hide ...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

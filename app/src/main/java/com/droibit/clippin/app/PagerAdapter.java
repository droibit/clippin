package com.droibit.clippin.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @auther kumagai
 * @since 1.0
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /** {@inheritDoc} */
    @Override
    public Fragment getItem(int position) {
        return EffectableFragment.newInstance(position);
    }

    /** {@inheritDoc} */
    @Override
    public int getCount() {
        return Fragments.sCenters.size();
    }

    /** {@inheritDoc} */
    @Override
    public CharSequence getPageTitle(int position) {
        return Fragments.sCenters.get(position).first;
    }
}

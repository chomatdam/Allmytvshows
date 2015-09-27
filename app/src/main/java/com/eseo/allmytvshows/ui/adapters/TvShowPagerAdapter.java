package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.ui.fragments.MainActivity.BestShowsFragment;
import com.eseo.allmytvshows.ui.fragments.MainActivity.MyShowsFragment;

/**
 * Created by Damien on 9/26/15.
 */
public class TvShowPagerAdapter extends FragmentPagerAdapter {

    static final String LOG_TAG = "TvShowPagerAdapter";

    private Context ctx;

    public TvShowPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ctx.getString(R.string.tabOne);
            case 1:
                return ctx.getString(R.string.tabTwo);
            default:
                return "Item " + (position + 1);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = Fragment.instantiate(ctx, MyShowsFragment.class.getName());
                break;
            }
            case 1: {
                fragment = Fragment.instantiate(ctx, BestShowsFragment.class.getName());
                break;
            }
        }
        return fragment;
    }

}

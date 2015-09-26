package com.eseo.allmytvshows.ui.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.ui.fragments.MainActivity.BestShowsFragment;
import com.eseo.allmytvshows.ui.fragments.MainActivity.MyShowsFragment;

/**
 * Created by Damien on 9/26/15.
 */
public class AppPagerAdapter extends FragmentPagerAdapter {

    private Activity activity;

    public AppPagerAdapter(AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return activity.getString(R.string.tabOne);
            case 1:
                return activity.getString(R.string.tabTwo);
            default:
                return "Item " + (position + 1);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyShowsFragment.newInstance();
            case 1:
                return BestShowsFragment.newInstance();
            default:
                return MyShowsFragment.newInstance();
        }
    }

}

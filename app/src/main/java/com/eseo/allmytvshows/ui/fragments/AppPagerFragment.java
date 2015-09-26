package com.eseo.allmytvshows.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.ui.adapters.AppPagerAdapter;
import com.eseo.allmytvshows.ui.views.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/26/15.
 */
public class AppPagerFragment extends Fragment {

    static final String LOG_TAG = "AppPagerFragment";

    private static AppPagerFragment instance;

    @Bind(R.id.sliding_tabs)
    public SlidingTabLayout mSlidingTabLayout;

    @Bind(R.id.viewpager)
    public ViewPager mViewPager;

    public static AppPagerFragment newInstance() {
        if (instance == null) {
            instance = new AppPagerFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager.setAdapter(new AppPagerAdapter(getChildFragmentManager(), getActivity()));
        mSlidingTabLayout.setViewPager(mViewPager);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Otto if needed
    }
}

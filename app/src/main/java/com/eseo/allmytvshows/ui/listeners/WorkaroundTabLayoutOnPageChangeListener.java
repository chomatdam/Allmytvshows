package com.eseo.allmytvshows.ui.listeners;

import android.support.design.widget.TabLayout;

import java.lang.ref.WeakReference;

/**
 * Created by Damien on 9/27/15.
 */
public class WorkaroundTabLayoutOnPageChangeListener extends TabLayout.TabLayoutOnPageChangeListener {
    private final WeakReference<TabLayout> mTabLayoutRef;

    public WorkaroundTabLayoutOnPageChangeListener(TabLayout tabLayout) {
        super(tabLayout);
        this.mTabLayoutRef = new WeakReference<>(tabLayout);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        final TabLayout tabLayout = mTabLayoutRef.get();
        if (tabLayout != null) {
            final TabLayout.Tab tab = tabLayout.getTabAt(position);
            if (tab != null) {
                tab.select();
            }
        }
    }
}

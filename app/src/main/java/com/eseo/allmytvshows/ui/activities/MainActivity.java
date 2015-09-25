package com.eseo.allmytvshows.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.ui.fragments.MainActivity.BestShowsFragment;
import com.eseo.allmytvshows.ui.fragments.MainActivity.MyShowsFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private Realm realm;
    private Toolbar toolbar;

    @Bind(R.id.materialViewPager)
    public MaterialViewPager mViewPager;
    @Bind(R.id.fab)
    public FloatingActionButton fab;
    public View logo;

    private static final Integer TABS_NUMBER = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realm = Realm.getInstance(this);

//        realm.beginTransaction();
//        realm.where(RealmTvShow.class).findAll().clear();
//        realm.commitTransaction();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        setTitle("");
        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
    }
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % TABS_NUMBER) {
                    case 0:
                        return MyShowsFragment.newInstance();
                    case 1:
                        return BestShowsFragment.newInstance();
                    default:
                        return MyShowsFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return TABS_NUMBER;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % TABS_NUMBER) {
                    case 0:
                        return getString(R.string.tabOne);
                    case 1:
                        return getString(R.string.tabTwo);
                }
                return "";
            }
        });

    mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
        @Override
        public HeaderDesign getHeaderDesign(int page) {
            switch (page) {
                case 0:
                    fab.show();
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                case 1:
                    fab.hide();
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
            }

            return null;
        }
    });

    mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
    mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());


    View logo = findViewById(R.id.logo_white);

    if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewPager.notifyHeaderChanged();
            Snackbar.make(getCurrentFocus(), "Yes, the title is clickable", Snackbar.LENGTH_SHORT).show();
        }
    });
}

    @Override
    public void onResume() {
        super.onResume();
        realm = Realm.getInstance(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TODO: implement menu

        return super.onOptionsItemSelected(item);
    }

    public Realm getRealm() {
        return realm;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

}

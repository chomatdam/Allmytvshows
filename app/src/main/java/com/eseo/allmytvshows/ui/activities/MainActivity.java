package com.eseo.allmytvshows.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.adapters.AppPagerAdapter;
import com.eseo.allmytvshows.ui.views.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MainActivity";

    private Realm realm;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.fab)
    public FloatingActionButton fab;

    @Bind(R.id.sliding_tabs)
    public SlidingTabLayout mSlidingTabLayout;

    @Bind(R.id.viewpager)
    public ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        setSupportActionBar(toolbar);

        clearDatabase(realm);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        mViewPager.setAdapter(new AppPagerAdapter(this));
        mSlidingTabLayout.setViewPager(mViewPager);

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

    private static void clearDatabase(Realm realm) {
        realm.beginTransaction();
        realm.where(RealmTvShow.class).findAll().clear();
        realm.commitTransaction();
    }

}

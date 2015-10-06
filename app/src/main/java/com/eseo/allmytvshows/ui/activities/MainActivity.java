package com.eseo.allmytvshows.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.adapters.TvShowPagerAdapter;
import com.eseo.allmytvshows.ui.listeners.WorkaroundTabLayoutOnPageChangeListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MainActivity";

    private Realm realm;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.drawerLayout)
    public DrawerLayout drawerLayout;

    @Bind(R.id.navigation)
    public NavigationView navigationView;

    @Bind(R.id.fab)
    public FloatingActionButton mFab;

    @Bind(R.id.tablayout)
    public TabLayout tabLayout;

    @Bind(R.id.viewpager)
    public ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        //toolbar
        toolbar.setTitle("");
        toolbar.setLogo(getResources().getDrawable(R.drawable.drawing));
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Welcome !", Snackbar.LENGTH_LONG).show();
            }
        });

        //navigation drawer
        drawerLayout.setStatusBarBackground(R.color.primary_dark);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        // TODO - Do something
                        break;
                    case R.id.nav_calendar:
                        break;
                    case R.id.nav_archived_tv_shows:
                        break;
                    case R.id.nav_deleted_tv_shows:
                        break;
                }
                return true;
            }
        });

        //clearDatabase(realm);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        //tabs and swipe between fragments
        PagerAdapter mAdapter = new TvShowPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new WorkaroundTabLayoutOnPageChangeListener(tabLayout));

        //fab listener (button used to add a specific tv show)
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTvShowActivity.class);
                startActivity(intent);
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private static void clearDatabase(Realm realm) {
        realm.beginTransaction();
        realm.where(RealmTvShow.class).findAll().clear();
        realm.commitTransaction();
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getInstance(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    public Realm getRealm() {
        return realm;
    }

}

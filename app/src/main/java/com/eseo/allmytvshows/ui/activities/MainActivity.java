package com.eseo.allmytvshows.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
    public FloatingActionButton mFab;

    @Bind(R.id.tablayout)
    public TabLayout tabLayout;

    @Bind(R.id.search_view)
    public MaterialSearchView searchView;

    @Bind(R.id.viewpager)
    public ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        toolbar.setTitle("");
        toolbar.setLogo(getResources().getDrawable(R.drawable.drawing));
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Welcome !", Snackbar.LENGTH_LONG).show();
            }
        });

        //clearDatabase(realm);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        PagerAdapter mAdapter = new TvShowPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new WorkaroundTabLayoutOnPageChangeListener(tabLayout));

        onPrepareSearchView();
        initFab();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
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

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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

    private void initFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTvShowActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onPrepareSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    public Realm getRealm() {
        return realm;
    }

}

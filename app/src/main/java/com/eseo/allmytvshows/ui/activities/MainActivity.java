package com.eseo.allmytvshows.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.adapters.TvShowPagerAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.karim.MaterialTabs;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MainActivity";

    private Realm realm;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.material_tabs)
    public MaterialTabs materialTabs;

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

        setSupportActionBar(toolbar);

        clearDatabase(realm);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        mViewPager.setAdapter(new TvShowPagerAdapter(this));
        materialTabs.setViewPager(mViewPager);

        onPrepareSearchView();

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

    private static void clearDatabase(Realm realm) {
        realm.beginTransaction();
        realm.where(RealmTvShow.class).findAll().clear();
        realm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
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

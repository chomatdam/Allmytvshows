package com.eseo.allmytvshows.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.season.ISeasonDao;
import com.eseo.allmytvshows.dao.season.impl.SeasonDaoImpl;
import com.eseo.allmytvshows.managers.RetrofitManager;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.ui.adapters.DetailEpisodeAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DetailEpisodeActivity extends Activity {

    private Realm realm;

    @Bind(R.id.detail_episode_content_layout)
    FrameLayout frameLayout;
    @Bind(R.id.episode_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.season_already_seen_button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_episode);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        long season = getIntent().getLongExtra("season", -1);
        final ISeasonDao iSeasonDao = new SeasonDaoImpl(realm);
        RealmSeason realmSeason = iSeasonDao.find(season);

        Picasso.with(this)
                .load(RetrofitManager.IMAGE_URL + realmSeason.getTvShow().getPoster_path())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        frameLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        final DetailEpisodeAdapter detailEpisodeAdapter = new DetailEpisodeAdapter(this, realmSeason);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailEpisodeAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"not plugged yet", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

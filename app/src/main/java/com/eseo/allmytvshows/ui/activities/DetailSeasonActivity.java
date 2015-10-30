package com.eseo.allmytvshows.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.season.ISeasonDao;
import com.eseo.allmytvshows.dao.season.impl.SeasonDaoImpl;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.dao.tvshow.impl.TvShowDaoImpl;
import com.eseo.allmytvshows.managers.RetrofitManager;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.adapters.DetailSeasonAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DetailSeasonActivity extends AppCompatActivity {

    @Bind(R.id.detail_season_tvshow_name)
    TextView textView;
    @Bind(R.id.detail_season_picture)
    ImageView imageView;
    @Bind(R.id.my_recycler_view)
    public RecyclerView recyclerView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_season);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        final Long realmTvShowId = getIntent().getLongExtra("tvshow", 0);
        final ITvShowDao iTvShowDao = new TvShowDaoImpl(realm);
        final RealmTvShow realmTvShow = iTvShowDao.find(realmTvShowId);

        final ISeasonDao iSeasonDao = new SeasonDaoImpl(realm);
        final RealmResults<RealmSeason> seasonResults = iSeasonDao.getSeasonsWithoutZero(realmTvShowId);
        final RealmList<RealmSeason> seasonRealmList = new RealmList<>();
        seasonRealmList.addAll(seasonResults);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmTvShow.setSeasons(seasonRealmList);
            }
        });

        textView.setText(realmTvShow.getOriginal_name());
        Picasso.with(this)
                .load(RetrofitManager.IMAGE_URL + realmTvShow.getPoster_path())
                .fit()
                .centerCrop()
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(DetailSeasonActivity.this)
                        .title(realmTvShow.getOriginal_name())
                        .content(realmTvShow.getOverview())
                        .neutralText("Close")
                        .show();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailSeasonAdapter(this, realmTvShow));

    }

    public Realm getRealm() {
        return realm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}

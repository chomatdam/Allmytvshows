package com.eseo.allmytvshows.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.dao.tvshow.impl.TvShowDaoImpl;
import com.eseo.allmytvshows.managers.RetrofitManager;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.adapters.DetailedTvShowAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class DetailedTvShowActivity extends AppCompatActivity {

    @Bind(R.id.detail_imageTvShow)
    public ImageView imageView;

    @Bind(R.id.detail_recycler_seasons)
    public RecyclerView recyclerView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tv_show);
        ButterKnife.bind(this);
        realm = Realm.getInstance(this);

        final Long realmTvShowId = getIntent().getLongExtra("tvshow", 0);
        final ITvShowDao iTvShowDao = new TvShowDaoImpl(realm);
        RealmTvShow realmTvShow = iTvShowDao.find(realmTvShowId);
        Picasso.with(this)
                .load(RetrofitManager.IMAGE_URL + realmTvShow.getPoster_path())
                .into(imageView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailedTvShowAdapter(this, realmTvShow));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}

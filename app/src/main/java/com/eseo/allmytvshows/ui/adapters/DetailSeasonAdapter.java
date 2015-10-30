package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.activities.DetailEpisodeActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.mobiwise.library.ProgressLayout;

/**
 * Created by Damien on 10/5/15.
 */
public class DetailSeasonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String LOG_TAG = DetailSeasonAdapter.class.getSimpleName();

    private Context ctx;

    private List<RealmSeason> seasons;

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.progressLayout)
        ProgressLayout progressLayout;
        @Bind(R.id.episodesSeen)
        TextView episodesSeen;
        @Bind({R.id.seasonName})
        TextView tvShowName;

        SeasonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DetailSeasonAdapter(final Context ctx, final RealmTvShow realmTvShow) {
        seasons = realmTvShow.getSeasons();
        this.ctx = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_seasons, parent, false);

        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {
            final SeasonViewHolder seasonViewHolder = (SeasonViewHolder) holder;
            final Calendar cal = Calendar.getInstance();
            final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            cal.setTime(sdf.parse(seasons.get(position).getAir_date()));
            final Integer seasonYear = cal.get(Calendar.YEAR);
            seasonViewHolder.tvShowName.setText(String.format(ctx.getResources().getString(
                    R.string.detail_season_number) + seasonYear + ") -", seasons.get(position).getSeason_number()));
            seasonViewHolder.episodesSeen.setText(seasons.get(position).getEpisodes().size() + " episodes");
            seasonViewHolder.progressLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, DetailEpisodeActivity.class);
                    intent.putExtra("season", seasons.get(position).getId());
                    ctx.startActivity(intent);
                }
            });
            Log.d("HEYHEY", String.valueOf((int)Math.round(Math.random()*100)));
            seasonViewHolder.progressLayout.setCurrentProgress((int)Math.round(Math.random()*100));
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Unknown date format");
        }
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }
}

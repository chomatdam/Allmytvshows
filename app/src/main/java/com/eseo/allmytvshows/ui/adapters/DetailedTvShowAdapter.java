package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.episode.IEpisodeDao;
import com.eseo.allmytvshows.dao.episode.impl.EpisodeDaoImpl;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.views.TouchCheckBox;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Damien on 10/5/15.
 */
public class DetailedTvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;

    private List<RealmSeason> seasons;

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.episodesSeen)
        TextView episodesSeen;
        @Bind({R.id.seasonName})
        TextView tvShowName;
        @Bind({R.id.checkboxSeason})
        TouchCheckBox seen;

        SeasonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DetailedTvShowAdapter(final Context ctx, final RealmTvShow realmTvShow) {
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
        final SeasonViewHolder seasonViewHolder = (SeasonViewHolder) holder;
        seasonViewHolder.tvShowName.setText(String.format(ctx.getResources().getString(
                R.string.detail_season_number), seasons.get(position).getSeason_number()));
        IEpisodeDao iEpisodeDao = new EpisodeDaoImpl(Realm.getInstance(ctx));
        final long nbEpisodesSeen = iEpisodeDao.getSeenEpisodeNumber(seasons.get(position).getId());
        final boolean atLeastOneEpisodeSeen = nbEpisodesSeen >= 1;
        seasonViewHolder.seen.setChecked(atLeastOneEpisodeSeen);
        seasonViewHolder.episodesSeen.setText(String.format("%d/%d", nbEpisodesSeen, seasons.get(position).getEpisodes().size()));

    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }
}

package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmEpisode;
import com.eseo.allmytvshows.model.realm.RealmSeason;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by Damien on 10/30/15.
 */
public class DetailEpisodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;

    private RealmList<RealmEpisode> episodes;

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {


        @Bind({R.id.detail_episode_ep_number})
        TextView episodeNumber;
        @Bind({R.id.detail_episode_ep_name})
        TextView episodeName;

        EpisodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DetailEpisodeAdapter(final Context ctx, final RealmSeason realmSeason) {
        episodes = realmSeason.getEpisodes();
        this.ctx = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_episodes, parent, false);

        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EpisodeViewHolder episodeViewHolder = (EpisodeViewHolder) holder;
        episodeViewHolder.episodeName.setText(episodes.get(position).getName() + " / " + episodes.get(position).getAir_date());
        episodeViewHolder.episodeNumber.setText("Episode " + (position+1));

    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}

package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.managers.RetrofitManager;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.views.TouchCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/19/15.
 */
public class MyShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static final String LOG_TAG = "MyShowsAdapter";

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private Context ctx;
    private List<RealmTvShow> contents;


    public static class TvShowViewHolder extends RecyclerView.ViewHolder {
        @Bind({R.id.imageView}) ImageView coverArt;
        @Bind({R.id.textView}) TextView tvShowName;
        @Bind({R.id.textView2}) TextView tvShowDetail;
        @Bind({R.id.checkboxBestTvShow}) TouchCheckBox added;

        TvShowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MyShowsAdapter(final Context ctx, final List<RealmTvShow> contents) {
        this.ctx = ctx;
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CELL /* TYPE_HEADER */;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_HEADER: {
                break;
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card, parent, false);
                return new TvShowViewHolder(view);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        switch (getItemViewType(i)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                TvShowViewHolder tvShowViewHolder = (TvShowViewHolder) viewHolder;
                tvShowViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowViewHolder.coverArt);
                tvShowViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                tvShowViewHolder.added.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

}

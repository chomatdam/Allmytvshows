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
import com.eseo.allmytvshows.managers.TvShowService;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.ui.views.TouchCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/19/15.
 */
public class BestTvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private Context ctx;
    private List<TvShow> contents;

    public static class TvShowSmallViewHolder extends RecyclerView.ViewHolder {
        @Bind({R.id.imageView}) ImageView coverArt;
        @Bind({R.id.textView}) TextView tvShowName;
        @Bind({R.id.textView2}) TextView tvShowDetail;
        @Bind({R.id.checkboxBestTvShow}) TouchCheckBox added;

        TvShowSmallViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TvShowBigViewHolder extends RecyclerView.ViewHolder {
        @Bind({R.id.imageView_big}) ImageView coverArt;
        @Bind({R.id.textView_big}) TextView tvShowName;
        @Bind({R.id.textView2_big}) TextView tvShowDetail;
        @Bind({R.id.checkboxBestTvShow_big}) TouchCheckBox added;

        TvShowBigViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public BestTvShowsAdapter(final Context ctx, final List<TvShow> contents) {
        this.ctx = ctx;
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new TvShowBigViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new TvShowSmallViewHolder(view) {
                };
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {

        switch (getItemViewType(i)) {
            case TYPE_HEADER:
                TvShowBigViewHolder tvShowBigViewHolder = (TvShowBigViewHolder) viewHolder;
                tvShowBigViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowBigViewHolder.coverArt);
                tvShowBigViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                tvShowBigViewHolder.added.setChecked(false);
                tvShowBigViewHolder.added.setOnCheckedChangeListener(new TouchCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(View buttonView, boolean isChecked) {
                        if (isChecked) {
                            //TODO: add element my tv shows
                            TvShowService tvShowService = new TvShowService(ctx, buttonView.getRootView(), contents.get(i));
                            tvShowService.getDataTVShow();
                            /*
                            Appel WS avec tvshow
                            OnChecked, tu fais un appel WS data tvshow
                            OnCompleted, store Realm
                            notifyAdapter myTvShows
                             */
                        }
                    }
                });
                break;
            case TYPE_CELL:
                TvShowSmallViewHolder tvShowSmallViewHolder = (TvShowSmallViewHolder) viewHolder;
                tvShowSmallViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowSmallViewHolder.coverArt);
                tvShowSmallViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                tvShowSmallViewHolder.added.setChecked(false);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}

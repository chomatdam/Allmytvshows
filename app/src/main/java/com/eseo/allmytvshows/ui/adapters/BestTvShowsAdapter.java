package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.dao.tvshow.impl.TvShowDaoImpl;
import com.eseo.allmytvshows.managers.AppApplication;
import com.eseo.allmytvshows.managers.RetrofitManager;
import com.eseo.allmytvshows.managers.TvShowService;
import com.eseo.allmytvshows.model.Data;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.activities.MainActivity;
import com.eseo.allmytvshows.ui.views.TouchCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/19/15.
 */
public class BestTvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

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
        //TODO: find a way to unregister from bus in this class
        AppApplication.getBus().register(this);
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
        final ITvShowDao iTvShowDao = new TvShowDaoImpl(((MainActivity) ctx).getRealm());
        final boolean tvShowStored = (iTvShowDao.findByName(contents.get(i).getOriginal_name()) == null ? false : true);
        switch (getItemViewType(i)) {
            case TYPE_HEADER:
                TvShowBigViewHolder tvShowBigViewHolder = (TvShowBigViewHolder) viewHolder;
                tvShowBigViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowBigViewHolder.coverArt);
                tvShowBigViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                //TODO: duplicated code (1)
                tvShowBigViewHolder.added.setChecked(tvShowStored);
                tvShowBigViewHolder.added.setOnCheckedChangeListener(new TouchCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(View buttonView, boolean isChecked) {
                        final boolean  tvShowStoredOnChanged = (iTvShowDao.findByName(contents.get(i).getOriginal_name()) == null ? false : true);
                        if (isChecked) {
                            TvShowService tvShowService = new TvShowService(ctx, buttonView.getRootView(), contents.get(i));
                            if (!tvShowStoredOnChanged) {
                                tvShowService.getDataTVShow();
                            }
                        } else {
                            if (tvShowStoredOnChanged) {
                                RealmTvShow realmTvShow = iTvShowDao.findByName(contents.get(i).getOriginal_name());
                                AppApplication.getBus().post(new Data(Data.NOTIFY_MY_SHOWS_ADAPTER, realmTvShow.getId()));
                                iTvShowDao.remove(realmTvShow);
                            }
                        }
                    }
                });
                //TODO: until here (1)
                break;
            case TYPE_CELL:
                TvShowSmallViewHolder tvShowSmallViewHolder = (TvShowSmallViewHolder) viewHolder;
                tvShowSmallViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowSmallViewHolder.coverArt);
                tvShowSmallViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                //TODO: duplicated code (2)
                tvShowSmallViewHolder.added.setChecked(tvShowStored);
                tvShowSmallViewHolder.added.setOnCheckedChangeListener(new TouchCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(View buttonView, boolean isChecked) {
                        if (isChecked) {
                            TvShowService tvShowService = new TvShowService(ctx, buttonView.getRootView(), contents.get(i));
                            if (!tvShowStored) {
                                tvShowService.getDataTVShow();
                            }
                        } else {
                            if (tvShowStored) {
                                final RealmTvShow realmTvShow = iTvShowDao.findByName(contents.get(i).getOriginal_name());
                                if (realmTvShow != null) {
                                    AppApplication.getBus().post(new Data(Data.NOTIFY_MY_SHOWS_ADAPTER, realmTvShow.getId()));
                                    iTvShowDao.remove(realmTvShow);
                                }
                            }
                        }
                    }
                });
                //TODO: until here (2)
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

}

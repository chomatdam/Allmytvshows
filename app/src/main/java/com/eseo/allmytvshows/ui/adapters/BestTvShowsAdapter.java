package com.eseo.allmytvshows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.dao.tvshow.impl.TvShowDaoImpl;
import com.eseo.allmytvshows.managers.TvShowApplication;
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

    static final String LOG_TAG = "BestTvShowsAdapter";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private Context ctx;
    private List<TvShow> contents;

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

    public BestTvShowsAdapter(final Context ctx, final List<TvShow> contents) {
        //TODO: find a way to unregister from bus in this class
        TvShowApplication.getBus().register(this);
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
                return new TvShowViewHolder(view) {
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
                break;
            case TYPE_CELL:
                TvShowViewHolder tvShowViewHolder = (TvShowViewHolder) viewHolder;
                tvShowViewHolder.tvShowName.setText(contents.get(i).getOriginal_name());
                Picasso .with(ctx)
                        .load(RetrofitManager.IMAGE_URL + contents.get(i).getPoster_path())
                        .into(tvShowViewHolder.coverArt);
                tvShowViewHolder.tvShowDetail.setText(contents.get(i).getNextEpisode());
                tvShowViewHolder.added.setChecked(tvShowStored);
                tvShowViewHolder.added.setOnCheckedChangeListener(new TouchCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final View buttonView, boolean isChecked) {
                        if (isChecked) {
                            addTvShow(buttonView);
                        } else {
                            removeTvShow();
                        }
                    }

                    private void addTvShow(final View buttonView) {
                        new MaterialDialog.Builder(ctx)
                                .title("Add a new TV show")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        if (!tvShowStored) {
                                            TvShowService tvShowService = new TvShowService(ctx, buttonView.getRootView(), contents.get(i));
                                            tvShowService.getDataTVShow();
                                        }
                                    }
                                })
                                .content("Are you sure to add '" + contents.get(i).getOriginal_name() + "' to your favorites shows ?")
                                .positiveText("Continue")
                                .negativeText("Abort")
                                .show();
                    }

                    private void removeTvShow() {
                        new MaterialDialog.Builder(ctx)
                                .title("Remove this TV show")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        if (tvShowStored) {
                                            final RealmTvShow realmTvShow = iTvShowDao.findByName(contents.get(i).getOriginal_name());
                                            if (realmTvShow != null) {
                                                TvShowApplication.getBus().post(new Data(Data.NOTIFY_MY_SHOWS_ADAPTER, realmTvShow.getId()));
                                                iTvShowDao.remove(realmTvShow);
                                            }
                                        }
                                    }
                                })
                                .content("Are you sure to add '" + contents.get(i).getOriginal_name() + "' to your favorites shows ?")
                                .positiveText("Continue")
                                .negativeText("Abort")
                                .show();
                    }
                });
            }
        }

    @Override
    public int getItemCount() {
        return contents.size();
    }

}

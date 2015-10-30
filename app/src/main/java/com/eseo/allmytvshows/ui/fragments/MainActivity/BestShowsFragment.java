package com.eseo.allmytvshows.ui.fragments.MainActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.managers.TvShowService;
import com.eseo.allmytvshows.model.SearchResultsPage;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.ui.adapters.BestTvShowsAdapter;
import com.eseo.allmytvshows.ui.listeners.NotifyAdapterListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/21/15.
 */
public class BestShowsFragment extends Fragment implements NotifyAdapterListener{

    static final String LOG_TAG = "BestShowsFragment";

    @Bind(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TvShow> mContentItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new BestTvShowsAdapter(getActivity(), mContentItems);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mAdapter);

        final TvShowService mTvShowService = new TvShowService(getActivity(), this);
        mTvShowService.getPopularTvShows();

    }

    @Override
    public void callbackBestTvShows(SearchResultsPage data) {
        mContentItems.addAll(data.getResults());
        mAdapter.notifyDataSetChanged();
    }
}

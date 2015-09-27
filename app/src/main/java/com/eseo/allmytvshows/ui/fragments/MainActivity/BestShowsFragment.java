package com.eseo.allmytvshows.ui.fragments.MainActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.managers.TvShowService;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.ui.adapters.BestTvShowsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Damien on 9/21/15.
 */
public class BestShowsFragment extends Fragment {

    static final String LOG_TAG = "BestShowsFragment";

    @Bind(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TvShow> mContentItems = new ArrayList<>();
    public FloatingActionButton mFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new BestTvShowsAdapter(getActivity(), mContentItems);
        mRecyclerView.setAdapter(mAdapter);

        final TvShowService mTvShowService = new TvShowService(getView(), mContentItems, mAdapter);
        mTvShowService.getPopularTvShows();

        //TODO: view.getRootView() not safe
        mFab = ButterKnife.findById(view.getRootView(), R.id.fab);
        mFab.hide();


    }

}

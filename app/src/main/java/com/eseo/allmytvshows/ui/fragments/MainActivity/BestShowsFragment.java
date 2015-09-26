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

    private static BestShowsFragment instance;

    @Bind(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private List<TvShow> mContentItems = new ArrayList<>();

    public static BestShowsFragment newInstance() {
        if (instance == null) {
            instance = new BestShowsFragment();
        }
        return instance;
    }

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new BestTvShowsAdapter(getActivity(), mContentItems);
        mRecyclerView.setAdapter(mAdapter);

        final TvShowService mTvShowService = new TvShowService(getView(), mContentItems, mAdapter);
        mTvShowService.getPopularTvShows();

    }

}

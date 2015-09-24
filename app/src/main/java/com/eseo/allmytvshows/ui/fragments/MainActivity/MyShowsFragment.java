package com.eseo.allmytvshows.ui.fragments.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.activities.SearchActivity;
import com.eseo.allmytvshows.ui.adapters.MyShowsAdapter;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MyShowsFragment extends Fragment {

    private static MyShowsFragment instance = null;
    @Bind(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;
    public FloatingActionButton mFab;
    private RecyclerView.Adapter mAdapter;
    private List<RealmTvShow> mContentItems = new ArrayList<>();

    public static MyShowsFragment newInstance() {
        if (instance == null) {
            return new MyShowsFragment();
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

        mAdapter = new RecyclerViewMaterialAdapter(new MyShowsAdapter(getActivity().getApplicationContext(), mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        final Realm realm = Realm.getInstance(getActivity());
        final RealmQuery<RealmTvShow> query = realm.where(RealmTvShow.class);
        final RealmResults<RealmTvShow> myShows = query.findAll();

        for (RealmTvShow show : myShows) {
            mContentItems.add(show);
        }

        //TODO: view.getRootView() not safe ?
        mFab = ButterKnife.findById(view.getRootView(), R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(),mRecyclerView,null);
    }

}

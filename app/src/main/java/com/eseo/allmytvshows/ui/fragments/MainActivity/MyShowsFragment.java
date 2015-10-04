package com.eseo.allmytvshows.ui.fragments.MainActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eseo.allmytvshows.R;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.dao.tvshow.impl.TvShowDaoImpl;
import com.eseo.allmytvshows.managers.TvShowApplication;
import com.eseo.allmytvshows.model.Data;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.ui.activities.MainActivity;
import com.eseo.allmytvshows.ui.adapters.MyShowsAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyShowsFragment extends Fragment {

    static final String LOG_TAG = "MyShowsFragment";

    @Bind(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private List<RealmTvShow> mContentItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TvShowApplication.getBus().register(this);
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

        final ITvShowDao iTvShowDao = new TvShowDaoImpl(((MainActivity)getActivity()).getRealm());
        for (RealmTvShow show : iTvShowDao.findAll()) {
            mContentItems.add(show);
        }

        mAdapter = new MyShowsAdapter(getActivity(), mContentItems);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final ITvShowDao iTvShowDao1 = new TvShowDaoImpl(((MainActivity) getActivity()).getRealm());
                iTvShowDao1.remove(mContentItems.get(position));
                mContentItems.remove(position);
                mAdapter.notifyItemRemoved(position);
                TvShowApplication.getBus().post(new Data(Data.REFRESH_ALL_DATA_BEST_SHOWS_ADAPTER));
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Subscribe
    public void getMessage(Data data) {

        if (data.getKey() == Data.REFRESH_ALL_DATA_MY_SHOWS_ADAPTER) {
            mContentItems.clear();
            //TODO: dangerous thing with context - listeners/EventBus
            ITvShowDao iTvShowDao = new TvShowDaoImpl(((MainActivity)getActivity()).getRealm());
            mContentItems.addAll(iTvShowDao.findAll());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TvShowApplication.getBus().unregister(this);
    }

}

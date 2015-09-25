package com.eseo.allmytvshows.managers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.model.SearchResultsPage;
import com.eseo.allmytvshows.model.Season;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.ui.activities.MainActivity;
import com.eseo.allmytvshows.ui.fragments.MainActivity.MyShowsFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Class where you get your asynchronous response from a WS with RxJava.
 * Created by Damien on 9/20/15.
 */
public class TvShowService {

    //Context for Realm
    private Context ctx;

    //Error(s) to display
    private View v;

    //data from WS
    private List<TvShow> mContentItems = new ArrayList<>();

    //notifyDataSetChanged
    private RecyclerView.Adapter mAdapter;


    /**
     * Constructor used for "my tv shows" screen, you only need the tvshow checked by user, ask more data from WS,
     * store data (and notify "my tv shows" screen).
     * @param v
     * @param item
     */
    public TvShowService(Context c, View v, TvShow item) {
        this.ctx = c;
        this.v = v;
        mContentItems.add(item);
    }

    /**
     * Constructor used for "Best tv shows" screen, you need to fulfill the list of tv shows returned by WS and notify the adapter,
     * you can also use the current view to display an error msg if necessary
     * @param v
     * @param mContentItems
     * @param adapter
     */
    public TvShowService(View v, List<TvShow> mContentItems, RecyclerView.Adapter adapter) {
        this.v = v;
        this.mContentItems = mContentItems;
        this.mAdapter = adapter;
    }

    public void getPopularTvShows() {

        final Observable<SearchResultsPage> observable = RetrofitManager
                                                                .newInstance()
                                                                .getService()
                                                                .getPopularTvShows(MovieDbService.API_KEY);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultsPage>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.log(Log.ERROR, this.getClass().getSimpleName(), "Higgs boson detected! Bailing out...");
                        Snackbar.make(v,
                                "Oops...",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(SearchResultsPage data) {
                        mContentItems.addAll(data.getResults());
                        mAdapter.notifyDataSetChanged();
                    }

                });
    }

    public void getDataTVShow() {
        if (!mContentItems.isEmpty()) {
            final TvShow tvShow = mContentItems.get(0);
            final Observable<TvShow> observable = RetrofitManager
                    .newInstance()
                    .getService()
                            //get TvShow with empty seasons
                    .getDataTVShow(mContentItems.get(0).getId(), MovieDbService.API_KEY)
                    //Observable because we have an asynchronous task to do (map = object -> object ;  flatmap = object -> Observable<?>)
                    .flatMap(new Func1<TvShow, Observable<TvShow>>() {
                        @Override
                        public Observable<TvShow> call(final TvShow tvShow) {
                            //'from', you deal with objects sequentially / 'just', you work with a list
                            return Observable.from(tvShow.getSeasons())
                                    .flatMap(new Func1<Season, Observable<Season>>() {
                                        @Override
                                        public Observable<Season> call(Season s) {
                                            //for each season, an API call to get all episodes of each season
                                            return RetrofitManager
                                                    .newInstance()
                                                    .getService()
                                                    .getDataSeason(tvShow.getId(), s.getSeason_number(), MovieDbService.API_KEY);
                                        }
                                    })
                                            //With WS callback, we will store inside an ArrayList the result (container, action to add object to the container)
                                    .collect(new Func0<List<Season>>() {
                                        @Override
                                        public List<Season> call() {
                                            return new ArrayList<Season>();
                                        }
                                    }, new Action2<List<Season>, Season>() {
                                        @Override
                                        public void call(List<Season> seasons, Season season) {
                                            seasons.add(season);
                                        }
                                    })
                                            //we were working on List<Season> so now we go back to a TvShow object
                                    .map(new Func1<List<Season>, TvShow>() {
                                        @Override
                                        public TvShow call(List<Season> seasons) {
                                            tvShow.getSeasons().clear();
                                            tvShow.getSeasons().addAll(seasons);
                                            return tvShow;
                                        }
                                    });
                        }
                    });

            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<TvShow>() {
                        @Override
                        public void call(TvShow tvShow) {
                            final RealmFactory realmFactory = RealmFactory.newInstance(((MainActivity)ctx).getRealm());
                            realmFactory.execute(tvShow);
                            //TODO: dangerous thing with context - listeners/EventBus
                            ((MainActivity)ctx).getmHandler().sendEmptyMessage(MyShowsFragment.UPDATE_MY_SHOWS);
                        }
                    });


        }

    }

}

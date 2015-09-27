package com.eseo.allmytvshows.managers;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.eseo.allmytvshows.model.Data;
import com.eseo.allmytvshows.model.SearchResultsPage;
import com.eseo.allmytvshows.model.Season;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.ui.activities.MainActivity;
import com.eseo.allmytvshows.ui.listeners.NotifyAdapterListener;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Class where you get your asynchronous response from a WS with RxJava.
 * Created by Damien on 9/20/15.
 */
public class TvShowService {

    //Context for Realm
    private Context ctx;

    //data from WS
    private TvShow tvShow;

    //notifyDataSetChanged
    private NotifyAdapterListener mInterface;

    /**
     * Constructor used for "Best tv shows" screen, you need to fulfill the list of tv shows returned by WS and notify the adapter,
     * you can also use the current view to display an error msg if necessary
     * @param c {@link Context}
     * @param mInterface {@link NotifyAdapterListener}
     */
    public TvShowService(Context c, NotifyAdapterListener mInterface) {
        TvShowApplication.getBus().register(this);
        this.ctx = c;
        this.mInterface = mInterface;
    }

    /**
     * Constructor used for "my tv shows" screen, you only need the tvshow checked by user, ask more data from WS,
     * store data (and notify "my tv shows" screen).
     * @param c {@link Context}
     * @param item {@link TvShow}
     */
    public TvShowService(Context c, TvShow item) {
        TvShowApplication.getBus().register(this);
        this.ctx = c;
        this.tvShow = item;
    }


    public void getPopularTvShows() {

        final Observable<SearchResultsPage> observable = RetrofitManager
                                                                .newInstance()
                                                                .getService()
                                                                .getPopularTvShows(MovieDbService.API_KEY);


        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultsPage>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                        mInterface = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.log(Log.ERROR, this.getClass().getSimpleName(), "Higgs boson detected! Bailing out...");
                    }

                    @Override
                    public void onNext(SearchResultsPage data) {
                        mInterface.callbackBestTvShows(data);
                    }

                });
    }

    public void getDataTVShow() {
        if (tvShow != null) {
            final Observable<TvShow> observable = RetrofitManager
                    .newInstance()
                    .getService()
                            //get TvShow with empty seasons
                    .getDataTVShow(tvShow.getId(), MovieDbService.API_KEY)
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
//                                    .collect(new Func0<List<Season>>() {
//                                        @Override
//                                        public List<Season> call() {
//                                            return new ArrayList<Season>();
//                                        }
//                                    }, new Action2<List<Season>, Season>() {
//                                        @Override
//                                        public void call(List<Season> seasons, Season season) {
//                                            seasons.add(season);
//                                        }
//                                    })
                                    .toList() // same  thing as collect
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
                            //TODO: find a way to unregister from bus in this class
                            TvShowApplication.getBus().post(new Data(Data.REFRESH_ALL_DATA_MY_SHOWS_ADAPTER));
                        }
                    });


        }

    }

}

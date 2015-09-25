package com.eseo.allmytvshows.managers;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Class where you create Retrofit service(s).
 * Created by Damien on 9/20/15.
 */
public class RetrofitManager {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private static RetrofitManager instance = null;
    private  MovieDbService service;

    /**
     * Constructor used to build MovieDB service.
     */
    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(MovieDbService.class);
    }

    /**
     * Method with Singleton pattern to instantiate only one time the MovieDB service.
     * @return realm {@link RetrofitManager}
     */
    public static RetrofitManager newInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    /**
     * Getter for Movie DB service
     * @return service {@link MovieDbService}
     */
    public MovieDbService getService() {
        return service;
    }

}

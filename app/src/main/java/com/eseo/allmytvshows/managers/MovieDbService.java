package com.eseo.allmytvshows.managers;

import com.eseo.allmytvshows.model.SearchResultsPage;
import com.eseo.allmytvshows.model.Season;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.RealmTvShow;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Retrofit interface for Movie DB API.
 * Created by Damien on 9/20/15.
 */
public interface MovieDbService {

    String API_KEY = "85942e21532423f95b0630459fa4101f";

    /**
     * This method will return all tv shows based on the tv show name given as a parameter.
     * @param name {@link RealmTvShow#getOriginal_name()}
     * @param apiKey {@link MovieDbService#API_KEY}
     * @return all tv shows found with this tv show name
     */
    @GET("search/tv")
    Observable<SearchResultsPage> getTvShows(
            @Query("query") String name,
            @Query("api_key") String apiKey);

    /**
     * This method will return top 10 tv shows
     * @param apiKey {@link MovieDbService#API_KEY}
     * @return best tv shows on the air returned
     */
    @GET("tv/popular")
    Observable<SearchResultsPage> getPopularTvShows(
            @Query("api_key") String apiKey);

    /**
     * Once you have your tv show with {@link MovieDbService#getTvShows(String, String)},
     * with the tv show id you ask for more details about the tv show (overview, seasons, etc...)
     * @param id {@link RealmTvShow#getId_moviedb()}
     * @param apiKey {@link MovieDbService#API_KEY}
     * @return detailed tv show
     */
    @GET("tv/{id}")
    Observable<TvShow> getDataTVShow(
            @Path("id") long id,
            @Query("api_key") String apiKey);

    /**
     * With the number of seasons found previously with {@link MovieDbService#getDataTVShow(long, String)},
     * you have to iterate over each season and return all episodes found for this season.
     * @param id {@link RealmTvShow#getId_moviedb()}
     * @param season_number {@link RealmSeason#getId()}
     * @param apiKey {@link MovieDbService#API_KEY}
     * @return all episodes of the selected season
     */
    @GET("tv/{id}/season/{season_number}")
    Observable<Season> getDataSeason(
            @Path("id") long id,
            @Path("season_number") int season_number,
            @Query("api_key") String apiKey);
}

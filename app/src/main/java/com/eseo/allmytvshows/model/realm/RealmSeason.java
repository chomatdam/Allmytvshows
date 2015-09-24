package com.eseo.allmytvshows.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Damien on 2/2/2015.
 */
public class RealmSeason extends RealmObject {

    @PrimaryKey
    private long id;
    private int season_number;
    private int episode_count;
    private String air_date;
    private String poster_path ;
    private RealmTvShow tvShow;
    private RealmList<RealmEpisode> episodes;

    public RealmSeason() {

    }

    public RealmSeason(long id, int season_number, int episode_count, String air_date,
                       String poster_path, RealmTvShow tvShow, RealmList<RealmEpisode> episodes) {
        this.id = id;
        this.season_number = season_number;
        this.episode_count = episode_count;
        this.air_date = air_date;
        this.poster_path = poster_path;
        this.tvShow = tvShow;
        this.episodes = episodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public RealmTvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(RealmTvShow tvShow) {
        this.tvShow = tvShow;
    }

    public RealmList<RealmEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(RealmList<RealmEpisode> episodes) {
        this.episodes = episodes;
    }
}

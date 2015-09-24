package com.eseo.allmytvshows.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Damien on 2/2/2015.
 */
public class Season implements Serializable {

    private long id;
    private long id_tvshow;
    private int season_number;
    private int episode_count;
    private String air_date;
    private String poster_path ;
    private List<Episode> episodes ;

    public int getNumberSeenEpisodes(){
        int nbSeenEpisodes = 0 ;
        for(Episode episode : episodes){
            if(episode.isSeen() == 1){
                nbSeenEpisodes++;
            }
        }
        return nbSeenEpisodes ;
    }

    public void updateSeenEpisodes(int seen){
        for(int i=0;i<episodes.size();i++){
            Episode episode = episodes.get(i);
            episode.setSeen(seen);
        }
    }

    public long getId_tvshow() {
        return id_tvshow;
    }

    public void setId_tvshow(long id_tvshow) {
        this.id_tvshow = id_tvshow;
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }



}

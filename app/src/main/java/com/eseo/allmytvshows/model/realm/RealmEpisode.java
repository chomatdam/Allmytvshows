package com.eseo.allmytvshows.model.realm;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmEpisode extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String air_date;
    private boolean seen;
    private RealmSeason season;

    public RealmEpisode() {

    }

    public RealmEpisode(long id, String name, String air_date, boolean seen, RealmSeason season) {
        this.id = id;
        this.name = name;
        this.air_date = air_date;
        this.seen = seen;
        this.season = season;
    }

    /**
     *  Getter episode id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter episode id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter Air date for this episode
     * @return air_date
     */
    public String getAir_date() {
        return air_date;
    }

    /**
     * Setter Air date for this episode
     * @param air_date
     */
    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    /**
     * Getter RealmEpisode name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter RealmEpisode name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter Seen status
     * @param seen
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Getter Seen status
     * @return seen
     */
    public boolean getSeen() {
        return seen;
    }

    public RealmSeason getSeason() {
        return season;
    }

    public void setSeason(RealmSeason season) {
        this.season = season;
    }
}

package com.eseo.allmytvshows.model;


import java.io.Serializable;

public class Episode implements Serializable {

    private long id;
    private long id_season;
    private String name;
    private String air_date;
    private int seen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_season() {
        return id_season;
    }

    public void setId_season(long id_season) {
        this.id_season = id_season;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }
}

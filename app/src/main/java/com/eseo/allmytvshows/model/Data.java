package com.eseo.allmytvshows.model;

/**
 * Created by Damien on 9/25/15.
 */
public class Data {

    public static final int NOTIFY_MY_SHOWS_ADAPTER = 1;
    public static final int REFRESH_ALL_DATA_MY_SHOWS_ADAPTER = 2;

    private int key;
    private long longValue;
    private String stringValue;

    public Data(final int key) {
        this.key = key;
    }

    public Data(final int key, final long longValue) {
        this.key = key;
        this.longValue = longValue;
    }

    public Data(final int key, final String stringValue) {
        this.stringValue = stringValue;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public long getLongValue() {
        return longValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}

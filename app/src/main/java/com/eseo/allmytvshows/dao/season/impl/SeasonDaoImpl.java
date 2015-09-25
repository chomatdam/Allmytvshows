package com.eseo.allmytvshows.dao.season.impl;

import com.eseo.allmytvshows.dao.impl.BaseDaoImpl;
import com.eseo.allmytvshows.dao.season.ISeasonDao;
import com.eseo.allmytvshows.model.realm.RealmSeason;

import io.realm.Realm;

/**
 * Created by Damien on 9/24/15.
 */
public class SeasonDaoImpl extends BaseDaoImpl<RealmSeason> implements ISeasonDao {

    public SeasonDaoImpl (Realm realm) {
        super(realm);
    }

    @Override
    public void removeSeasonZero() {
        //TODO

    }


}

package com.eseo.allmytvshows.dao.tvshow.impl;

import com.eseo.allmytvshows.dao.impl.BaseDaoImpl;
import com.eseo.allmytvshows.dao.tvshow.ITvShowDao;
import com.eseo.allmytvshows.model.realm.RealmTvShow;
import com.eseo.allmytvshows.model.realm.constants.RealmTvShowConstants;

import io.realm.Realm;
import io.realm.RealmQuery;


/**
 * Created by Damien on 9/24/15.
 */
public class TvShowDaoImpl extends BaseDaoImpl<RealmTvShow> implements ITvShowDao {

    public TvShowDaoImpl (Realm realm) {
        super(realm);
    }

    @Override
    public boolean checkDuplicatedTvShow(final String tvShowName) {
        //TODO

        return false;
    }

    @Override
    public RealmTvShow findByName(final String name) {
        final RealmQuery<RealmTvShow> query = realm.where(clazz).equalTo(RealmTvShowConstants.Vars.NAME_TV_SHOW, name);
        final RealmTvShow myShow = query.findFirst();

        return myShow;
    }

}

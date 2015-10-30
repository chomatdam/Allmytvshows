package com.eseo.allmytvshows.dao.season.impl;

import com.eseo.allmytvshows.dao.impl.BaseDaoImpl;
import com.eseo.allmytvshows.dao.season.ISeasonDao;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.constants.RealmSeasonConstants;
import com.eseo.allmytvshows.model.realm.constants.RealmTvShowConstants;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Damien on 9/24/15.
 */
public class SeasonDaoImpl extends BaseDaoImpl<RealmSeason> implements ISeasonDao {

    public SeasonDaoImpl (Realm realm) {
        super(realm);
    }

    @Override
    public RealmResults<RealmSeason> getSeasonsWithoutZero(final long tvShowId) {

        final RealmQuery<RealmSeason> query = realm.where(clazz)
                .equalTo(RealmSeasonConstants.Vars.TV_SHOW_SEASON + "." + RealmTvShowConstants.Vars.ID_TV_SHOW, tvShowId)
                .notEqualTo(RealmSeasonConstants.Vars.SEASON_NUMBER_SEASON, 0);

                RealmResults<RealmSeason> retValue = query.findAllSorted(RealmSeasonConstants.Vars.SEASON_NUMBER_SEASON);
                return retValue;
    }

    @Override
    public RealmSeason getSeason(final long tvShowId, final long seasonNumber) {
        final RealmQuery<RealmSeason> query = realm.where(clazz)
                .equalTo(RealmSeasonConstants.Vars.SEASON_NUMBER_SEASON, seasonNumber)
                .equalTo(RealmSeasonConstants.Vars.TV_SHOW_SEASON + "." + RealmTvShowConstants.Vars.ID_TV_SHOW, tvShowId);
        final RealmSeason retValue = query.findFirst();

        return retValue;
    }

}

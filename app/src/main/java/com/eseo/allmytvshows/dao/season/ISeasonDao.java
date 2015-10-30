package com.eseo.allmytvshows.dao.season;

import com.eseo.allmytvshows.dao.IBaseDao;
import com.eseo.allmytvshows.model.realm.RealmSeason;

import io.realm.RealmResults;

/**
 * Created by Damien on 9/24/15.
 */
public interface ISeasonDao extends IBaseDao<RealmSeason> {

    public RealmResults<RealmSeason> getSeasonsWithoutZero(long tvShowId);

    public RealmSeason getSeason(final long tvShowId, final long seasonId);

}

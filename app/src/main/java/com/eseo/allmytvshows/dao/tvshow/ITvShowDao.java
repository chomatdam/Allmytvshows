package com.eseo.allmytvshows.dao.tvshow;

import com.eseo.allmytvshows.dao.IBaseDao;
import com.eseo.allmytvshows.model.realm.RealmTvShow;

/**
 * Created by Damien on 9/24/15.
 */
public interface ITvShowDao extends IBaseDao<RealmTvShow> {

    boolean checkDuplicatedTvShow(final String tvShowName);

    RealmTvShow findByName(final String name);

}

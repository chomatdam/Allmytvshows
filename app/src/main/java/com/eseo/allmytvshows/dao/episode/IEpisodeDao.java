package com.eseo.allmytvshows.dao.episode;

import com.eseo.allmytvshows.dao.IBaseDao;
import com.eseo.allmytvshows.model.realm.RealmEpisode;

import io.realm.RealmList;

/**
 * Created by Damien on 9/24/15.
 */
public interface IEpisodeDao extends IBaseDao<RealmEpisode>{

    public long getSeenEpisodeNumber(final long seasonId);

    public RealmList<RealmEpisode> getEpisodesNotAiredYet (final String nameTvShow);


}

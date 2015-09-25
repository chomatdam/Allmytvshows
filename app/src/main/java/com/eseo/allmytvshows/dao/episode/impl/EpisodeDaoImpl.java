package com.eseo.allmytvshows.dao.episode.impl;

import com.eseo.allmytvshows.dao.episode.IEpisodeDao;
import com.eseo.allmytvshows.dao.impl.BaseDaoImpl;
import com.eseo.allmytvshows.model.realm.RealmEpisode;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Damien on 9/24/15.
 */
public class EpisodeDaoImpl extends BaseDaoImpl<RealmEpisode> implements IEpisodeDao {

    public EpisodeDaoImpl (Realm realm) {
        super(realm);
    }

    @Override
    public int getSeenEpisodeNumber(final String nameTvShow, final int seasonNumber) {
        //TODO

        return 1;
    }

    @Override
    public int getSeenEpisodeNumber(final String nameTvShow) {
        //TODO

        return 1;
    }

    @Override
    public RealmList<RealmEpisode> getEpisodesNotAiredYet (final String nameTvShow) {
        //TODO

        return null;
    }

}

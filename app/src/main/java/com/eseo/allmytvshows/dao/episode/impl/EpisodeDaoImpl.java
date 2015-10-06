package com.eseo.allmytvshows.dao.episode.impl;

import com.eseo.allmytvshows.dao.episode.IEpisodeDao;
import com.eseo.allmytvshows.dao.impl.BaseDaoImpl;
import com.eseo.allmytvshows.model.realm.RealmEpisode;
import com.eseo.allmytvshows.model.realm.constants.RealmEpisodeConstants;
import com.eseo.allmytvshows.model.realm.constants.RealmSeasonConstants;

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
    public long getSeenEpisodeNumber(final long seasonId) {
        final long nbEpisodes = realm.where(RealmEpisode.class)
                .equalTo(RealmEpisodeConstants.Vars.SEASON_EPISODE + "." + RealmSeasonConstants.Vars.ID_SEASON, seasonId)
                .equalTo(RealmEpisodeConstants.Vars.SEEN_EPISODE, true)
                .count();
        return nbEpisodes;
    }

    @Override
    public RealmList<RealmEpisode> getEpisodesNotAiredYet (final String nameTvShow) {
        //TODO

        return null;
    }

}

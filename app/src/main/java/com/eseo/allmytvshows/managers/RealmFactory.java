package com.eseo.allmytvshows.managers;

import com.eseo.allmytvshows.model.Episode;
import com.eseo.allmytvshows.model.Season;
import com.eseo.allmytvshows.model.TvShow;
import com.eseo.allmytvshows.model.realm.RealmEpisode;
import com.eseo.allmytvshows.model.realm.RealmSeason;
import com.eseo.allmytvshows.model.realm.RealmTvShow;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/** Factory where you only create Realm objects from objects returned by MovieDB API.
 * Here we don't use Realm instance available in activities, we get another one and close it once everything is done.
 * Created by Damien on 9/24/15.
 */
public class RealmFactory {

    private static Realm realm = null;
    private static RealmFactory instance = null;

    private RealmFactory(Realm realm) {
        this.realm = realm;
    }

    public static RealmFactory newInstance(Realm realm) {
        if (instance == null || realm == null) {
            instance = new RealmFactory(realm);
        }
        return instance;
    }


    public long getNextKey(Class mClass) {
        long value = realm.where(mClass).maximumInt("id") + 1;
        value = (value < 1 ? 1 : value);

        return value;
    }


    public <T> RealmObject execute (final T object) {

        if (object instanceof TvShow) {
            realm.beginTransaction();
            RealmObject retValue = toRealmTvShow((TvShow) object);
            realm.commitTransaction();
            return retValue;
        }

        throw new IllegalArgumentException("Unsupported object type " + object);
    }

    private RealmTvShow toRealmTvShow(TvShow clazz) {
        final long idValue = getNextKey(RealmTvShow.class);

        RealmTvShow realmTvShow = realm.createObject(RealmTvShow.class);
        realmTvShow.setId(idValue);
        realmTvShow.setId_moviedb(clazz.getId());
        realmTvShow.setNextEpisode(clazz.getNextEpisode() == null ? "Air date not known" : clazz.getNextEpisode());
        realmTvShow.setOverview(clazz.getOverview());
        realmTvShow.setOriginal_name(clazz.getOriginal_name());
        realmTvShow.setPoster_path(clazz.getPoster_path());

        if (CollectionUtils.isNotEmpty(clazz.getSeasons())) {
            realmTvShow.setSeasons(toRealmSeasons(realmTvShow, clazz.getSeasons()));
        }

        return realmTvShow;
    }

    public RealmList<RealmSeason> toRealmSeasons(RealmTvShow realmTvShow, List<Season> clazzList) {
        long idValue = getNextKey(RealmSeason.class);

        RealmList<RealmSeason> realmSeasons = new RealmList<>();
        for (Season clazz : clazzList) {
            RealmSeason realmSeason = realm.createObject(RealmSeason.class);
            realmSeason.setId(idValue++);
            realmSeason.setSeason_number(clazz.getSeason_number());
            realmSeason.setEpisode_count(clazz.getEpisode_count());
            realmSeason.setAir_date(clazz.getAir_date());
            realmSeason.setTvShow(realmTvShow);

            if (CollectionUtils.isNotEmpty(clazz.getEpisodes())) {
                realmSeason.setEpisodes(toRealmEpisodes(realmSeason, clazz.getEpisodes()));
            }
            realmSeasons.add(realmSeason);
        }

        return realmSeasons;
    }

    public RealmList<RealmEpisode> toRealmEpisodes(RealmSeason realmSeason, List<Episode> clazzList) {
        long idValue = getNextKey(RealmEpisode.class);

        final RealmList<RealmEpisode> episodes = new RealmList<RealmEpisode>();
        for (Episode clazz : clazzList) {
            RealmEpisode realmEpisode = realm.createObject(RealmEpisode.class);
            realmEpisode.setId(idValue++);
            realmEpisode.setAir_date(clazz.getAir_date());
            realmEpisode.setName(clazz.getName());
            realmEpisode.setSeen(false);
            realmEpisode.setSeason(realmSeason);

            episodes.add(realmEpisode);
        }

        return episodes;
    }
}

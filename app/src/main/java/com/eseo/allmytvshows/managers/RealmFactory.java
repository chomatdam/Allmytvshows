package com.eseo.allmytvshows.managers;

import android.content.Context;

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

/**
 * Created by Damien on 9/24/15.
 */
public class RealmFactory {

    private static Realm realm = null;
    private static Context context = null;
    private static RealmFactory instance = null;


    public static RealmFactory newInstance(Context ctx) {
        if (instance == null) {
            instance = new RealmFactory();
            context = ctx;
            realm = Realm.getInstance(ctx);
        }
        return instance;
    }

    public Realm getRealmInstance() {
        if (realm == null) {
            realm = Realm.getInstance(context);
        }
        return realm;
    }

    private void checkRealmInstance() {
        if (realm == null) {
            realm = Realm.getInstance(context);
        }
    }


    public long getNextKey(Class mClass) {
        checkRealmInstance();
        long value = realm.where(mClass).maximumInt("id") + 1;
        value = (value < 1 ? 1 : value);

        return value;
    }

    public void commitAndClose() {
        realm.commitTransaction();
        realm.close();
    }


    public <T> RealmObject execute (final T object) {
        if (object instanceof TvShow) {
            return toRealmTvShow((TvShow) object);
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

package com.eseo.allmytvshows.dao.impl;

/**
 * Created by Damien on 9/25/15.
 */

import com.eseo.allmytvshows.dao.IBaseDao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class BaseDaoImpl<T extends RealmObject> implements IBaseDao<T> {

    protected final Class<T> clazz;
    protected Realm realm = null;

    protected BaseDaoImpl() {
        super();
        clazz = (Class<T>) getGenericClass(this);
    }

    protected BaseDaoImpl (Realm realm) {
        clazz = (Class<T>) getGenericClass(this);
        this.realm = realm;
    }

    public T find(long id) {
        final RealmQuery<T> query = realm.where(clazz).equalTo("id", id);
        final T myShow = query.findFirst();

        return myShow;
    }

    public RealmResults<T> findAll() {
        final RealmQuery<T> query = realm.where(clazz);
        final RealmResults<T> myShows = query.findAll();

        return myShows;
    }

    public void update(T o) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(o);
        realm.commitTransaction();
    }

    public void remove(T o) {
        realm.beginTransaction();
        o.removeFromRealm();
        realm.commitTransaction();
    }

    private Class<?> getGenericClass(final Object object) {
        final Class<? extends Object> classCurrent = object.getClass();
        Type superclassType = classCurrent.getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            superclassType = classCurrent.getSuperclass().getGenericSuperclass();
            if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
                return null;
            }
        }
        final Type[] objectTypes = ((ParameterizedType) superclassType).getActualTypeArguments();
        if (objectTypes != null) {
            final Type type = objectTypes[0];
            if (type instanceof Class<?>) {
                return (Class<?>) type;
            }
        }

        return null;
    }
}

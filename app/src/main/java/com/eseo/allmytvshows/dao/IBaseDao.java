package com.eseo.allmytvshows.dao;

import java.util.List;

/**
 * Created by Damien on 9/25/15.
 */
public interface IBaseDao<T> {

    T find(long id);

    List<T> findAll();

    void update(T o);

    void remove(T o);

}

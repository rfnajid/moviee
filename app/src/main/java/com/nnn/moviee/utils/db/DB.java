package com.nnn.moviee.utils.db;

import android.content.Context;

import com.nnn.moviee.model.realm.RealmMovie;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ridhaaaaazis on 19/02/18.
 */

public class DB {

    private static final int DBVERSION=1;

    public static void init(Context context){
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(DBVERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static List<RealmMovie> getFavorites(Realm realm){

        RealmQuery<RealmMovie> query = realm.where(RealmMovie.class);
        RealmResults<RealmMovie> realmResult = query.findAll();

        return realmResult.subList(0,realmResult.size());

    }

    public static void addFavorite(Realm realm, RealmMovie realmMovie){
        realm.beginTransaction();
        realm.insert(realmMovie);
        realm.commitTransaction();
    }

    public static void removeFavorite(Realm realm,RealmMovie realmMovie){
        realm.beginTransaction();
        realm.where(RealmMovie.class).equalTo("id", realmMovie.getId()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static boolean isFavorite(Realm realm, RealmMovie realmMovie){
        RealmMovie m = realm.where(RealmMovie.class).equalTo("id", realmMovie.getId()).findFirst();
        return m != null;
    }

}

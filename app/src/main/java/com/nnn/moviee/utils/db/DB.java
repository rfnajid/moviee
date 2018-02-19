package com.nnn.moviee.utils.db;

import android.content.Context;

import com.nnn.moviee.model.Movie;

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

    public static List<Movie> getFavorites(Realm realm){

        RealmQuery<Movie> query = realm.where(Movie.class);
        RealmResults<Movie> realmResult = query.findAll();

        return realmResult.subList(0,realmResult.size());

    }

    public static void addFavorite(Realm realm, Movie movie){
        realm.beginTransaction();
        realm.insert(movie);
        realm.commitTransaction();
    }

    public static void removeFavorite(Realm realm,Movie movie){
        realm.beginTransaction();
        realm.where(Movie.class).equalTo("id",movie.getId()).findAll().deleteAllFromRealm();
        realm.commitTransaction();

    }

    public static boolean isFavorite(Realm realm, Movie movie){
        Movie m = realm.where(Movie.class).equalTo("id",movie.getId()).findFirst();
        return m==null?false:true;
    }

}

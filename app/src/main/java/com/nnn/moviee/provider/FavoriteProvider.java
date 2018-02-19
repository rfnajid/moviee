package com.nnn.moviee.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nnn.moviee.model.Movie;
import com.nnn.moviee.utils.db.DB;

import java.util.List;

import io.realm.Realm;


/**
 * Created by ridhaaaaazis on 19/02/18.
 */

public class FavoriteProvider extends ContentProvider {

    public static final String AUTHORITY = "com.nnn.moviee";
    public static final String OBJECT ="favorite";


    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY,OBJECT,1);
    }


    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {

        DB.init(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Realm realm= Realm.getDefaultInstance();
        List<Movie> list= DB.getFavorites(realm);

        MatrixCursor cursor = new MatrixCursor(new String[]{
                "id","title","posterPath","overview","releaseDate","rating"
        });

        for (Movie m : list){
            cursor.newRow()
                    .add("id",m.getId())
                    .add("title",m.getTitle())
                    .add("posterPath",m.getPosterPath())
                    .add("overview",m.getOverview())
                    .add("releaseDate",m.getReleaseDate())
                    .add("rating",m.getRating());
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package com.nnn.moviee.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.nnn.moviee.R;
import com.nnn.moviee.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ridhaaaaazis on 16/03/18.
 */

public class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Movie> widgetItems = new ArrayList<>();
    private Context context;
    private int widgetId;

    public static final String AUTHORITY = "com.nnn.moviee";
    public static final String OBJECT ="favorite";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+OBJECT);


    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        S.log("STACK REMOTE CREATE ");
        //onDataSetChanged();

    }

    @Override
    public void onDataSetChanged() {
        S.log("ON DATA SET CHANGED");
        Cursor cursor = context.getContentResolver().query(CONTENT_URI,null,null,null,null);

        widgetItems.clear();
        while (cursor.moveToNext()){

            widgetItems.add(new Movie(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("posterPath")),
                    cursor.getString(cursor.getColumnIndex("overview")),
                    cursor.getString(cursor.getColumnIndex("releaseDate")),
                    cursor.getString(cursor.getColumnIndex("rating"))
            ));
        }

        S.log("count widget item : "+ widgetItems.size());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Movie movie = widgetItems.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        rv.setTextViewText(R.id.widget_text, movie.getTitle());

        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(movie.getPosterPath185())
                    .submit()
                    .get();

            rv.setImageViewBitmap(R.id.widget_image,bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle b = new Bundle();
        b.putLong("id",movie.getId());
        b.putString("title",movie.getTitle());
        b.putString("posterPath",movie.getPosterPath());
        b.putString("overview",movie.getOverview());
        b.putString("releaseDate",movie.getReleaseDate());
        b.putString("rating",movie.getRating());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(b);

        rv.setOnClickFillInIntent(R.id.widget_image, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



}
package com.nnn.moviee.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.nnn.moviee.R;
import com.nnn.moviee.activity.MovieActivity;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.service.StackWidgetService;
import com.nnn.moviee.utils.S;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_OPEN = "OPEN";



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);

        views.setRemoteAdapter( R.id.stack_view, intent);

        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent openIntent = new Intent(context, FavoriteWidgetProvider.class);

        openIntent.setAction(FavoriteWidgetProvider.ACTION_OPEN);
        openIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent openPendingIntent = PendingIntent.getBroadcast(context, 0, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, openPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
// There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(ACTION_OPEN)) {
            Bundle b = intent.getExtras();

            Movie m = new Movie(
                    b.getLong("id"),
                    b.getString("title"),
                    b.getString("posterPath"),
                    b.getString("overview"),
                    b.getString("releaseDate"),
                    b.getString("rating")
            );

            S.goTo(context, MovieActivity.class,m);
        }else if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            S.log("gonna update widget");
            appWidgetManager.notifyAppWidgetViewDataChanged(ids,R.id.stack_view);

        }
        super.onReceive(context, intent);


    }
}


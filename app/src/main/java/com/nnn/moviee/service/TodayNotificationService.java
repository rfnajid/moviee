package com.nnn.moviee.service;

import android.app.PendingIntent;
import android.content.Intent;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.nnn.moviee.activity.MovieActivity;
import com.nnn.moviee.api.MovieApi;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.model.TodayNotification;
import com.nnn.moviee.model.response.ListMovieResponse;
import com.nnn.moviee.utils.NotificationUtil;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.Pref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class TodayNotificationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {

        S.log("Today Notif start job !!");

        if(!Pref.getToday(getApplicationContext())) {
            S.log("But, pref is off, so cancel notify");
            return false;
        }
        sendRequest();

        S.log("Today Notif succesfully running");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        S.log("Today Notif Stop job");
        return false;
    }

    void createNotif(TodayNotification notif){
        NotificationUtil notification = new NotificationUtil(getApplicationContext());
        notification.setContent(
                notif.getTitle(),
                notif.getContent(),
                notif.getSubtitle(),
                notif.getType()
        );

        Intent i = new Intent(getApplicationContext(), MovieActivity.class);
        i.putExtra("extra",notif.getMovie());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,i,0);

        notification.setPendingIntent(pendingIntent);

        notification.sendNotify();
    }


    void sendRequest(){
        Retrofit retrofit = S.getRetrofit();
        MovieApi movieApi = retrofit.create(MovieApi.class);
        execCall(movieApi.getUpcoming());
    }

    void execCall(Call<ListMovieResponse> call){
        call.enqueue(new Callback<ListMovieResponse>() {
            @Override
            public void onResponse(Call<ListMovieResponse> call, Response<ListMovieResponse> response) {
                if(response.code()==200){
                    ListMovieResponse movieResponse = response.body();
                    filterToNotif(movieResponse.getListResults());
                }else{
                    S.toast(getApplicationContext(),response.code()+" : "+response.message());
                }

            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                S.toast(getApplicationContext(),"Unexpected Error");
            }
        });
    }

    void filterToNotif(List<Movie> movies){
        List<TodayNotification> notifs=new ArrayList<>();
        for(Movie m : movies){
            boolean isToday = S.isStringToday(m.getReleaseDate());
            if(isToday){
                TodayNotification notif =  new TodayNotification(getApplicationContext(),m);
                notifs.add(notif);
                createNotif(notif);
            }
        }

        S.log("TOday Notif : "+notifs.size());
    }

}

package com.nnn.moviee.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.nnn.moviee.activity.MainActivity;
import com.nnn.moviee.utils.NotificationUtil;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.Pref;


/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class DailyNotificationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {

        S.log("Daily Notif start job !!");

        if(!Pref.getDaily(getApplicationContext())) {
            S.log("But, pref is off, so cancel notify");
            return false;
        }

        Bundle bundle = job.getExtras();

        String title = bundle.getString("title");
        String type = bundle.getString("type");
        String content = bundle.getString("content");
        String subtitle = bundle.getString("subtitle");

        NotificationUtil notif = new NotificationUtil(getApplicationContext());
        notif.setContent(
                title,
                content,
                subtitle,
                type
        );

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);

        notif.setPendingIntent(pendingIntent);

        notif.sendNotify();

        S.log("Notif : "+title+" succesfully running");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        S.log("Notif Stop job");
        return false;
    }
}

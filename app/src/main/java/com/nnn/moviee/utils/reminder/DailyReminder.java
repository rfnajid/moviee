package com.nnn.moviee.utils.reminder;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.nnn.moviee.model.NotificationModel;
import com.nnn.moviee.service.DailyNotificationService;
import com.nnn.moviee.utils.NotificationUtil;
import com.nnn.moviee.utils.S;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class DailyReminder {
    Context context;
    Calendar time;

    String TAG = "DAILY_JOB";

    NotificationModel notif;


    public DailyReminder(Context context) {
        this.context = context;
        time=getDailyTime();
    }

    public static Calendar getDailyTime(){
        Calendar timer = Calendar.getInstance();
        timer.set(Calendar.HOUR, 7);
        timer.set(Calendar.MINUTE, 0);
        timer.set(Calendar.SECOND, 0);
        timer.set(Calendar.MILLISECOND, 0);
        timer.set(Calendar.AM_PM, Calendar.AM);

        return timer;
    }

    public void setNotif(NotificationModel notif) {
        this.notif=notif;
    }

    public void setTestTime(int sec){
        time = Calendar.getInstance();
        time.add(Calendar.SECOND,sec);
    }

    public void run(){
        sendNotif();
    }

    void sendNotif(){
        S.log("DailyReminder : try to running");

        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar now = Calendar.getInstance();

        long diff = time.getTimeInMillis() - now.getTimeInMillis();

        if (diff < 0) {
            time.add(Calendar.DAY_OF_MONTH, 1);
            diff = time.getTimeInMillis() - now.getTimeInMillis();
        }

        int start = (int)(diff/1000);
        int end   = start+3;

        S.log("start : "+start+" end : "+end);


        Bundle bundle = new Bundle();
        bundle.putString("type", NotificationUtil.ID_DAILY);
        bundle.putString("title", notif.getTitle());
        bundle.putString("content", notif.getContent());
        bundle.putString("subtitle", notif.getSubtitle());


        Job job = jobDispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(DailyNotificationService.class)
                .setExtras(bundle)
                .setTag(TAG)
                .setReplaceCurrent(true)
                .setRecurring(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(start,end))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .build();

        jobDispatcher.mustSchedule(job);

        S.log("DailyReminder run finished");
    }
}

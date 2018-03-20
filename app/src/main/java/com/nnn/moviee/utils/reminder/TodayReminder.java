package com.nnn.moviee.utils.reminder;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.nnn.moviee.model.NotificationModel;
import com.nnn.moviee.service.TodayNotificationService;
import com.nnn.moviee.utils.S;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class TodayReminder {
    Context context;
    Calendar time;

    String TAG = "TODAY_JOB";

    List<NotificationModel> notifications;

    public static Calendar getTodayTime(){
        Calendar timer = Calendar.getInstance();
        timer.set(Calendar.HOUR, 8);
        timer.set(Calendar.MINUTE, 0);
        timer.set(Calendar.SECOND, 0);
        timer.set(Calendar.MILLISECOND, 0);
        timer.set(Calendar.AM_PM, Calendar.AM);

        return timer;
    }

    public TodayReminder(Context context) {
        this.context = context;
        time = getTodayTime();
    }

    public void setTestTime(int sec){
        time = Calendar.getInstance();
        time.add(Calendar.SECOND,sec);
    }

    public void run(){
        sendNotif();
    }

    void sendNotif(){
        S.log("TodayReminder : try to running");

        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Calendar now = Calendar.getInstance();

        long diff = time.getTimeInMillis() - now.getTimeInMillis();

        if (diff < 0) {
            time.add(Calendar.DAY_OF_MONTH, 1);
            diff = time.getTimeInMillis() - now.getTimeInMillis();
        }

        int start = (int)(diff/1000);
        int end   = start+3;

        S.log("start : "+start+" end : "+end);


        Job job = jobDispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(TodayNotificationService.class)
                .setTag(TAG)
                .setReplaceCurrent(true)
                .setRecurring(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(start,end))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .build();

        jobDispatcher.mustSchedule(job);

        S.log("TodayReminder run finished");
    }

}

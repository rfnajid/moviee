package com.nnn.moviee.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.nnn.moviee.R;
import com.nnn.moviee.model.NotificationModel;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class NotificationUtil{

    public static final String ID_DAILY = "ID_DAILY";
    public static final String ID_TODAY = "ID_TODAY";
    public static final String DAILY    = "DAILY REMINDER";
    public static final String TODAY    = "TODAY REMINDER";

    NotificationModel notif;

    Context context;
    PendingIntent pendingIntent;

    NotificationManager manager;

    public NotificationUtil(Context context) {
        this.context=context;
        manager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        createDailyChannel();
        createTodayChannel();
    }

    void createDailyChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelDaily = new NotificationChannel(
                    ID_DAILY,
                    DAILY,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channelDaily.enableLights(true);
            channelDaily.enableVibration(true);
            channelDaily.setLightColor(Color.RED);
            manager.createNotificationChannel(channelDaily);
        }
    }

    void createTodayChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelToday = new NotificationChannel(
                    ID_TODAY,
                    TODAY,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channelToday.enableLights(true);
            channelToday.enableVibration(true);
            channelToday.setLightColor(Color.BLUE);
            manager.createNotificationChannel(channelToday);
        }
    }

    public void setContent(String title,String content,String subtitle,String type){
        notif=new NotificationModel(title,content,subtitle,type);
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public void sendNotify(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context,getType())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle(getTitle())
                .setContentText(getContent())
                .setSubText(getSubtitle())
                .setAutoCancel(true);

        int id = new Random().nextInt(99)+1;
        manager.notify(id,notification.build());
    }


    public String getTitle() {
        return notif.getTitle();
    }

    public String getContent() {
        return notif.getContent();
    }

    public String getSubtitle() {
        return notif.getSubtitle();
    }

    public String getType() {
        return notif.getType();
    }
}

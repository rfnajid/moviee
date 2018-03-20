package com.nnn.moviee.utils.reminder;

import android.content.Context;

import com.nnn.moviee.model.NotificationModel;
import com.nnn.moviee.utils.NotificationUtil;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.Pref;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class ReminderHelper {
    public static void createDailyReminder(Context context){
        if(Pref.getDaily(context)) {
            S.log("Daily active");
            NotificationModel notif = new NotificationModel(context, NotificationUtil.ID_DAILY);

            DailyReminder reminder = new DailyReminder(context);
            reminder.setNotif(notif);
            reminder.run();
        }
    }

    public static void createTodayReminder(Context context){
        if(Pref.getToday(context)) {
            S.log("Today Active");
            TodayReminder reminder = new TodayReminder(context);
            reminder.run();
        }
    }
}

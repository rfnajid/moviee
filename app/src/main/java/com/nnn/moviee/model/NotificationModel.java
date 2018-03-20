package com.nnn.moviee.model;

import android.content.Context;

import com.nnn.moviee.R;
import com.nnn.moviee.utils.NotificationUtil;

import java.io.Serializable;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class NotificationModel implements Serializable {
    String title;
    String content;
    String subtitle;
    String type;

    public NotificationModel() {
    }

    public NotificationModel(String title, String content, String subtitle, String type) {
        this.title = title;
        this.content = content;
        this.subtitle = subtitle;
        this.type = type;
    }

    public NotificationModel(Context context,String type){
        this.type=type;
        if(type.equals(NotificationUtil.ID_DAILY)){
            title=context.getString(R.string.daily_title);
            content=context.getString(R.string.daily_content);
            subtitle=context.getString(R.string.daily_subtitle);
        }else{
            title=context.getString(R.string.today_title);
            content=context.getString(R.string.today_content);
            subtitle=context.getString(R.string.today_subtitle);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

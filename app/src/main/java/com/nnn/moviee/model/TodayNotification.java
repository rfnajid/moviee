package com.nnn.moviee.model;

import android.content.Context;

import com.nnn.moviee.utils.NotificationUtil;
import com.nnn.moviee.utils.S;

import java.io.Serializable;

/**
 * Created by ridhaaaaazis on 20/03/18.
 */

public class TodayNotification extends NotificationModel implements Serializable {

    Movie movie;

    public TodayNotification() {

    }

    public TodayNotification(Context context,Movie movie){
        super(context, NotificationUtil.ID_TODAY);
        this.movie=movie;
        title=movie.getTitle();
        content=title+" "+content;

        S.log("create today : "+movie.getTitle());
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

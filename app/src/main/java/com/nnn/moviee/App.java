package com.nnn.moviee;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nnn.moviee.utils.db.DB;


/**
 * Created by ridhaaaaazis on 31/01/18.
 */

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        DB.init(this);

    }
}

package com.nnn.moviee;

import android.app.Application;

import com.facebook.stetho.Stetho;


/**
 * Created by ridhaaaaazis on 31/01/18.
 */

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

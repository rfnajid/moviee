package com.nnn.moviee.utils.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.nnn.moviee.R;
import com.nnn.moviee.utils.S;

import java.util.Locale;

/**
 * Created by ridhaaaaazis on 03/02/18.
 */

public class Pref {

    //private static String PREF="pref";

    //public static int DBVERSION=2;

    public static SharedPreferences.Editor getEditor(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx).edit();
    }

    public static SharedPreferences get(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setString(Context ctx,String key,String value){
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context ctx, String key){
        SharedPreferences pref = get(ctx);
        return pref.getString(key,"");
    }

    public static void setBoolean(Context ctx,String key,boolean on){
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putBoolean(key,on);
        editor.commit();
    }

    public static boolean getBoolean(Context ctx,String key,boolean def){
        SharedPreferences pref = get(ctx);
        return pref.getBoolean(key,def);
    }

    public static void loadLocale(Context ctx){
        String code = getString(ctx,ctx.getString(R.string.key_language));
        String lang = "en";
        switch (code){
            case "1":
                lang = "id";
                break;
        }

        S.log("load Locale : "+lang);

        Locale locale = new Locale(lang);
        Configuration config = ctx.getResources().getConfiguration();
        config.locale = locale;
        ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());
    }

    public static boolean getDaily(Context ctx){
        return getBoolean(ctx,ctx.getString(R.string.key_daily),true);
    }

    public static boolean getToday(Context ctx){
        return getBoolean(ctx,ctx.getString(R.string.key_today),true);
    }

/*
    public static void saveObject(Context ctx, String name, Object obj){
        TinyDB db = new TinyDB(ctx);
        db.putObject(name,obj);
    }


    public static Object loadObject(Context ctx,String name,Class cls){
        TinyDB db = new TinyDB(ctx);
        try{
            return db.getObject(name,cls);
        }catch (NullPointerException nullEx){
            return null;
        }
    }

    public static void deleteObject(Context ctx, String name){
        TinyDB db = new TinyDB(ctx);
        db.remove(name);
        S.log("remove tiny db : "+name);
    }*/
}


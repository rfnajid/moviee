package com.nnn.moviee.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ridhaaaaazis on 25/01/18.
 */

public class S {

    public static String TAG="CATALOG";

    private static String API="https://api.themoviedb.org/3/";
    private static String API_KEY="377f89ac1b62ae7925710b6c51986759";

    public static String getAPIUrl(){
        return API;
    }


    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void goTo(Context ctx, Class cls) {
        Intent i = new Intent(ctx, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    public static void goTo(Context ctx, Class cls, Serializable s) {
        Intent i = new Intent(ctx, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("extra", s);
        ctx.startActivity(i);
    }

    public static void goTo(Context ctx,Class cls, String s){
        Intent i = new Intent(ctx,cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("extra",s);
        ctx.startActivity(i);
    }

    public static void goTo(Context ctx, Class cls, Bundle b) {
        Intent i = new Intent(ctx, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(b);
        ctx.startActivity(i);
    }

    public static int getResourceId(Context ctx, String uri){
        int res = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());
        return res;
    }

    public static Retrofit getRetrofit(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();


        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(S.getAPIUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }


    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean hasInternetAccess(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }

    public static boolean isStringNull(String str){
        return (str == null || str.isEmpty());
    }


    public static void log(String message){
        Log.d(TAG,message);
    }
}


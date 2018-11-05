package com.home24task;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import okhttp3.Cache;

public class App extends Application {

    private static Context context;
    private static App instance;

    private static final int CACHE_SIZE = 20 * 1024 * 1024;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) instance.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static Cache getNetworkCache() {
        return new Cache(instance.getCacheDir(), CACHE_SIZE);
    }


    public static void setContext(Context context) {
        App.context = context;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
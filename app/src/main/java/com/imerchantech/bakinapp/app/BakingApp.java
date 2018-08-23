package com.imerchantech.bakinapp.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BakingApp extends Application {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        Log.d("onCreateOfAppCalled", "fromOnCreate");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        // checkPermission();

    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        BakingApp.sharedPreferences = sharedPreferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    public static void setEditor(SharedPreferences.Editor editor) {
        BakingApp.editor = editor;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BakingApp.context = context;
    }
}

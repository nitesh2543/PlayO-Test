package com.example.nitesh.playo;

import android.app.Application;
import android.os.StrictMode;

public class PlayOApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}

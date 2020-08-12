package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IDd5dYwruK0FxC98RUiRsCSJS7uLwq3rJ2gSArBq")
                // if defined
                .clientKey("Dfq0EuE7UJ7ydaiTImh8Sg2b4nW0xgQRys3JWABF")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

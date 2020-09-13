package com.example.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OmgbojyYJADyggM3L1cQfhAk2K424mBKoDpl44aj")
                .clientKey("LLCBS07D3k08uz3aTXHzq9IuMexokyrTiid4Y9Fn")
                .server("https://parseapi.back4app.com/")
                .build()
        );




    }
}

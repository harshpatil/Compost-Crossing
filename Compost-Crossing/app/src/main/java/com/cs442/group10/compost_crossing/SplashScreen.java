package com.cs442.group10.compost_crossing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by cheth on 11/6/2016.
 */
public class SplashScreen extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_SCREEN_DELAY);
    }
}

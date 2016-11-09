package com.cs442.group10.compost_crossing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by cheth on 11/6/2016.
 */
public class SplashScreen extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashRelativeLayout);
        ImageView splashImage = (ImageView) findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);

        animation.reset();
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate);
        splashImage.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_SCREEN_DELAY);
    }
}

package com.cs442.cbhaskar.compostcrossing;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* ImageView personImage = (ImageView) findViewById(R.id.composterImg);
        personImage.setImageResource(R.mipmap.ic_launcher);*/

    }

    public void invokePickupInfoActivity(View v){
        Intent i = new Intent(this, CompostPickupInfoActivity.class);
        startActivity(i);
    }
}

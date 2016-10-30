package com.cs442.group10.compost_crossing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button Composter= (Button)findViewById(R.id.compostButton);
        final Button residentButton= (Button)findViewById(R.id.residentButton);

        Composter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), ViewListFragment.class);
                startActivity(i);

            }
        });

        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

//                Intent i = new Intent(getBaseContext(), "give the required class name");
                startActivity(i);

            }
        });
    }
}

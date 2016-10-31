package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs442.group10.compost_crossing.R;

public class ComposterRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_registeration);


        final Button createProfileButton= (Button)findViewById(R.id.createProfileButton);
        createProfileButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

//                Intent i = new Intent(getBaseContext(), "give the required class name");
//                startActivity(i);
            }
        });


    }
}

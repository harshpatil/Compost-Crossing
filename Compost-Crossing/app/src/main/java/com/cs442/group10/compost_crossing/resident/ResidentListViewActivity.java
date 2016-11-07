package com.cs442.group10.compost_crossing.resident;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs442.group10.compost_crossing.R;

public class ResidentListViewActivity extends AppCompatActivity {

    Button history;
    Button nearByComposter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_list_view);

        history = (Button) findViewById(R.id.btnResidentHistory);
        nearByComposter = (Button) findViewById(R.id.btnComposterNearBy);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistoryButton();
            }
        });

        nearByComposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNearByComposterButton();
            }
        });
    }

    public void back(View v){
        onBackPressed();
    }

    public void navigateToNewAdForm(View v){
        //navigating to posting new ad fragment goes here
    }

    public void onClickHistoryButton(){
    }

    public void onClickNearByComposterButton(){
    }
}

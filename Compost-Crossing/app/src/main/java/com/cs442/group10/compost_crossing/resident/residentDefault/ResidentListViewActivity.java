package com.cs442.group10.compost_crossing.resident.residentDefault;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.createAd.AdCreation;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentAdsHistory;
import com.cs442.group10.compost_crossing.resident.nearByComposter.NearByComposter;

public class ResidentListViewActivity extends AppCompatActivity {

    Button history;
    Button nearByComposter;
    Button postNewAd;
    Button goToHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_list_view);

        history = (Button) findViewById(R.id.btnResidentHistory);
        nearByComposter = (Button) findViewById(R.id.btnComposterNearBy);
        postNewAd = (Button) findViewById(R.id.btnPostAd);
        goToHomePage = (Button) findViewById(R.id.btnBackResidentListView);

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

        postNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingPostNewAd();
            }
        });

        goToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingGoToHomePage();
            }
        });
    }

    public void back(View v){
        onBackPressed();
    }

    public void navigateToNewAdForm(View v){
    }

    public void onClickHistoryButton(){

        Intent intent = new Intent(this, ResidentAdsHistory.class);
        startActivity(intent);
    }

    public void onClickNearByComposterButton(){

        Intent intent = new Intent(this, NearByComposter.class);
        startActivity(intent);
    }

    public void onClickingPostNewAd(){

        Intent intent = new Intent(this, AdCreation.class);
        startActivity(intent);
    }

    public void onClickingGoToHomePage(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

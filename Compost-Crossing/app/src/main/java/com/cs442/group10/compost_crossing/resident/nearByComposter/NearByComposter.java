package com.cs442.group10.compost_crossing.resident.nearByComposter;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.ResidentListViewActivity;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentHistoryFragment;

public class NearByComposter extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_by_composter_activity);

        FragmentManager fragmentManager = getFragmentManager();
        NearByComposterFragment orderHistoryFragment = (NearByComposterFragment) fragmentManager.findFragmentById(R.id.ResidentNearByComposterFragment);

        backButton = (Button) findViewById(R.id.backButtonResidentNearByComposterPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingBackButton();
            }
        });
    }

    public void onClickingBackButton(){

        Intent intent = new Intent(this, ResidentListViewActivity.class);
        startActivity(intent);
    }
}

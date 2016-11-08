package com.cs442.group10.compost_crossing.resident.nearByComposter;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.ResidentListViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NearByComposter extends AppCompatActivity {

    Button backButton;
    List<Composter> composterList = new ArrayList<Composter>();
    NearByComposterAdapter nearByComposterAdapter;
    ListView nearByComposterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Composters");
//        ref.push();
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();
//
//                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){
//
//                    Map<String,String> compostAdMap = compostAdListMap.getValue();
//
//                    if(String.valueOf(compostAdMap.get("Zipcode").subString(0, 2) == currentUserZipcode.subString(0.2)){
//
//                        Composter composter = new Composter();
//                        composter.setName(String.valueOf(compostAdMap.get("Name")));
//                        composter.setAddress(String.valueOf(compostAdMap.get("Address")));
//                        composter.setZipcode(String.valueOf(compostAdMap.get("ZipCode")));
//
//                        composterList.add(composter);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("DataBase", "Failed to read value.", databaseError.toException());
//            }
//        });

        // Remove this once data is fetched from firebase
        Composter composter = new Composter();
        composter.setName("Chicago Composter");
        composter.setAddress("2901, S. King Drive");
        composter.setZipcode("60616");

        composterList.add(composter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_by_composter_activity);

        FragmentManager fragmentManager = getFragmentManager();
        NearByComposterFragment nearByComposterFragment = (NearByComposterFragment) fragmentManager.findFragmentById(R.id.ResidentNearByComposterFragment);
        nearByComposterAdapter = new NearByComposterAdapter(this, R.layout.resident_nearby_composter_fragment, composterList);
        nearByComposterFragment.setListAdapter(nearByComposterAdapter);
        nearByComposterListView = nearByComposterFragment.getListView();
        nearByComposterAdapter.notifyDataSetChanged();


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

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
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;
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
    Composter composter;
    List<Composter> composterList = new ArrayList<Composter>();
    NearByComposterAdapter nearByComposterAdapter;
    ListView nearByComposterListView;
    String currentUserZipCode = "60616";
    int imageId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("composterRegisteration");
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();

                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){

                    Map<String,String> compostAdMap = compostAdListMap.getValue();

                    String composterZip = String.valueOf(compostAdMap.get("zipcode")).substring(0, 3);
                    String currentUserPin = currentUserZipCode.substring(0, 3);
                    Log.i("PINCODE", composterZip);
                    Log.i("PINCODE", currentUserPin);

                    if(composterZip.equals(currentUserPin)){

                        composter = new Composter();
                        switch (imageId%10){
                            case 0 : composter.setImageId(R.drawable.company_1); break;
                            case 1 : composter.setImageId(R.drawable.company_2); break;
                            case 2 : composter.setImageId(R.drawable.company_3); break;
                            case 3 : composter.setImageId(R.drawable.company_4); break;
                            case 4 : composter.setImageId(R.drawable.company_5); break;
                            case 5 : composter.setImageId(R.drawable.company_6); break;
                            case 6 : composter.setImageId(R.drawable.company_7); break;
                            case 7 : composter.setImageId(R.drawable.company_8); break;
                            case 8 : composter.setImageId(R.drawable.company_9); break;
                            default : composter.setImageId(R.drawable.company_10);
                        }
                        imageId ++;
                        composter.setName(String.valueOf(compostAdMap.get("name")));
                        composter.setAddress(String.valueOf(compostAdMap.get("address")) + " " + String.valueOf(compostAdMap.get("city")) + " " + String.valueOf(compostAdMap.get("zipcode")));
                        composterList.add(composter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

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

package com.cs442.group10.compost_crossing.resident.historyPage;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.newsArticle.News;
import com.cs442.group10.compost_crossing.resident.ResidentListViewActivity;
import com.cs442.group10.compost_crossing.resident.ResidentListViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HarshPatil on 11/7/16.
 */
public class ResidentAdsHistory extends AppCompatActivity {

    Button backButton;
    List<Ads> adsList = new ArrayList<Ads>();
    ResidentHistoryAdapter residentHistoryAdapter;
    ListView residentHistoryListView;
    String buyerName = "";
    Ads ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("adDetails");
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();

                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){

                    Map<String,String> compostAdMap = compostAdListMap.getValue();

                    if(String.valueOf(compostAdMap.get("sold")).contains("true")){

                        ads = new Ads();
                        String buyerId = String.valueOf(compostAdMap.get("buyerId"));

                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference ref2 = database2.getReference("composterRegisteration/"+buyerId + "/name");
                        ref2.push();
                        ref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                buyerName = dataSnapshot.getValue(String.class);
                                ads.setBuyerName(buyerName);
                                Log.i("BUYERNAME:", buyerName);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Log.i("ADSLIST",compostAdMap.get("title"));
                        Log.i("ADSLIST",compostAdMap.get("sold"));

                        ads.setTitle(String.valueOf(compostAdMap.get("title")));
                        ads.setCost(String.valueOf(compostAdMap.get("cost")));
                        ads.setWeight(String.valueOf(compostAdMap.get("weight")));
                        ads.setSold(String.valueOf(compostAdMap.get("sold")));

                        adsList.add(ads);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

        Log.i("ADSLIST", String.valueOf(adsList.size()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_ads_history);

        FragmentManager fragmentManager = getFragmentManager();
        ResidentHistoryFragment residentHistoryFragment = (ResidentHistoryFragment) fragmentManager.findFragmentById(R.id.ResidentHistoryFragment);
        residentHistoryAdapter = new ResidentHistoryAdapter(this, R.layout.resident_history_fragment, adsList);
        residentHistoryFragment.setListAdapter(residentHistoryAdapter);
        residentHistoryListView = residentHistoryFragment.getListView();
        residentHistoryAdapter.notifyDataSetChanged();

        backButton = (Button) findViewById(R.id.backButtonResidentHistoryPage);
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

package com.cs442.group10.compost_crossing.resident;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.CompostAd;
import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

public class ResidentListViewFragment extends Fragment {


    private static final String AD_ID_COL = "Ad_Id";
    private static final String TITLE_COL = "Title";
    private static final String WEIGHT_COL = "Weight";

    Map<String, String> compostAdsMap = new HashMap<String,String>();

    public ResidentListViewFragment() {
    }

    public static ResidentListViewFragment newInstance(int columnCount) {
        ResidentListViewFragment fragment = new ResidentListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resident_item, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.residentItemListView);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("adDetails");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                compostAdsMap = new HashMap<String, String>();
                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();
                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){
                    Map<String,String> compostAdMap = compostAdListMap.getValue();
                    String compostAd = String.valueOf(compostAdMap.get(TITLE_COL)) + "-" + String.valueOf(compostAdMap.get(WEIGHT_COL)) + "lbs";
                    compostAdsMap.put(compostAdListMap.getKey(), compostAd);
                    System.out.println(compostAdMap.get("Title"));

                    ResidentListViewAdapter residentListViewAdapter = new ResidentListViewAdapter(getActivity(), compostAdsMap);
                    listView.setAdapter(residentListViewAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error retrieving data in ResidentListView");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

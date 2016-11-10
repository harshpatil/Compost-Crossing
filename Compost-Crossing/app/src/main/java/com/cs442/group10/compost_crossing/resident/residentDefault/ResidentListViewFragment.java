package com.cs442.group10.compost_crossing.resident.residentDefault;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ResidentListViewFragment extends Fragment {

    private static final String AD_TABLE = "adDetails";
    private static final String AD_ID_COL = "adId";
    private static final String TITLE_COL = "title";
    private static final String WEIGHT_COL = "weight";
    private static final String BUYER_ID_COL = "buyerId";

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
        final TextView emptyTextView = (TextView) view.findViewById(R.id.emptyAdListForResident);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AD_TABLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                compostAdsMap = new HashMap<String, String>();
                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();
                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()) {
                    Map<String, String> compostAdMap = compostAdListMap.getValue();

                    String buyerId = compostAdMap.get(BUYER_ID_COL);
                    if (buyerId != null) {
                        if(buyerId.equals("NULL")) {
                            String compostAd = String.valueOf(compostAdMap.get(TITLE_COL)) + "-" + String.valueOf(compostAdMap.get(WEIGHT_COL));
                            compostAdsMap.put(compostAdListMap.getKey(), compostAd);

                            ResidentListViewAdapter residentListViewAdapter = new ResidentListViewAdapter(getActivity(), compostAdsMap);
                            listView.setAdapter(residentListViewAdapter);
                            listView.setEmptyView(emptyTextView);
                        }
                    }
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

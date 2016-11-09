package com.cs442.group10.compost_crossing.Composter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.ResidentListViewAdapter;
import com.cs442.group10.compost_crossing.resident.ResidentListViewFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ComposterListViewFragment extends Fragment {
    private static final String AD_TABLE = "adDetails";
    private static final String ID_COL = "id";
    private static final String AD_ID_COL = "adId";
    private static final String TITLE_COL = "title";
    private static final String WEIGHT_COL = "weight";
    private static final String COST_COL = "cost";
    private static final String BUYER_ID_COL = "buyerId";
    private static final String BUYER_NAME_COL = "buyerName";
    private static final String ADDRESS_COL = "address";
    private static final String CITY_COL = "city";
    private static final String ZIP_CODE_COL = "zipCode";
    private static final String STATE_COL = "state";
    private static final String DROP_COL = "drop";
    private static final String SOLD_COL = "sold";

    Map<String, String> compostAdsMap = new HashMap<String,String>();
    Map<String, AdDetail> adDetailMap = new HashMap<String, AdDetail>();

    public ComposterListViewFragment() {
    }

    public static ComposterListViewFragment newInstance(int columnCount) {
        ComposterListViewFragment fragment = new ComposterListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_composter_list_view, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.composterItemListView);
        final TextView emptyTextView = (TextView) view.findViewById(R.id.emptyAdListForComposter);

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
                    String weight = compostAdMap.get(WEIGHT_COL);
                    String title = compostAdMap.get(TITLE_COL);

                    final AdDetail adDetail = new AdDetail(compostAdMap.get(ID_COL),title, compostAdMap.get(ADDRESS_COL),compostAdMap.get(CITY_COL),compostAdMap.get(STATE_COL),
                            compostAdMap.get(ZIP_CODE_COL),compostAdMap.get(COST_COL),compostAdMap.get(DROP_COL),compostAdMap.get(SOLD_COL),weight,
                            compostAdMap.get(BUYER_ID_COL),compostAdMap.get(BUYER_NAME_COL));

                    if (buyerId != null) {
                        if(buyerId.equals("NULL")) {
                            final String compostAd = String.valueOf(title) + "-" + String.valueOf(weight);
                            compostAdsMap.put(compostAdListMap.getKey(), compostAd);
                            adDetailMap.put(compostAdListMap.getKey(), adDetail);

                            ComposterListViewAdapter composterListViewAdapter = new ComposterListViewAdapter(getActivity(), compostAdsMap);
                            listView.setAdapter(composterListViewAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                                    String key = (String) adDetailMap.keySet().toArray()[position];

                                    Intent compostDetailIntent = new Intent(getActivity().getApplicationContext(),CompostDetailActivity.class);
                                    Bundle compostAdBundle = new Bundle();
                                    compostAdBundle.putSerializable("compostAd", adDetailMap.get(key));
                                    compostDetailIntent.putExtras(compostAdBundle);
                                    startActivity(compostDetailIntent);
                                }
                            });

                            listView.setEmptyView(emptyTextView);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error retrieving data in ComposterListView");
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

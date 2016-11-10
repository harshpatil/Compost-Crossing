package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ComposterListViewFragment extends Fragment {
    private static final String AD_TABLE = "adDetails";
    private static final String RESIDENT_REG_TABLE = "residentRegisteration";
    private static final String ID_COL = "id";
    private static final String OWNER_NAME_COL = "ownerName";
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
        final RelativeLayout loadingLayout = (RelativeLayout) view.findViewById(R.id.loadingPanel);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(RESIDENT_REG_TABLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingLayout.setVisibility(View.GONE);
                compostAdsMap = new HashMap<String, String>();
                Map<String, Map<String,Object>> residentRegMap = (Map<String, Map<String,Object>>) dataSnapshot.getValue();
                for(Map.Entry<String, Map<String,Object>> residentMap: residentRegMap.entrySet()) {
                    Map<String, Object> residentRecordMap = residentMap.getValue();

                    if (!residentRecordMap.get("adlist").equals(" ")) {
                        Map<String, Map<String, String>> compostAdListMap = (Map<String, Map<String, String>>) residentRecordMap.get("adlist");
                        for (Map.Entry<String, Map<String, String>> compostAdMap : compostAdListMap.entrySet()) {
                            Map<String, String> adDetailsMap = compostAdMap.getValue();

                            String buyerId = adDetailsMap.get(BUYER_ID_COL);
                            String weight = adDetailsMap.get(WEIGHT_COL);
                            String title = adDetailsMap.get(TITLE_COL);
                            String id = adDetailsMap.get(ID_COL);
                            String ownerName = (String) residentRecordMap.get("name");
                            String ownerPhone = (String) residentRecordMap.get("phone");
                            String address = adDetailsMap.get(ADDRESS_COL);
                            String city = adDetailsMap.get(CITY_COL);
                            String zipCode = adDetailsMap.get(ZIP_CODE_COL);
                            String cost = adDetailsMap.get(COST_COL);
                            String drop = adDetailsMap.get(DROP_COL);
                            String sold = adDetailsMap.get(SOLD_COL);
                            String buyerName =  adDetailsMap.get(BUYER_NAME_COL);
                            String state = adDetailsMap.get(STATE_COL);

                            final AdDetail adDetail = new AdDetail(id, ownerName, ownerPhone, title, address, city, state, zipCode, cost, drop, sold, weight, buyerId, buyerName);

                            if (buyerId != null) {
                                if (buyerId.equals("NULL")) {
                                    final String compostAd = String.valueOf(title) + "-" + String.valueOf(weight);
                                    compostAdsMap.put(compostAdMap.getKey(), compostAd);
                                    adDetailMap.put(compostAdMap.getKey(), adDetail);

                                    ComposterListViewAdapter composterListViewAdapter = new ComposterListViewAdapter(getActivity(), compostAdsMap);
                                    listView.setAdapter(composterListViewAdapter);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                                            String key = (String) adDetailMap.keySet().toArray()[position];

                                            Intent compostDetailIntent = new Intent(getActivity().getApplicationContext(), CompostDetailActivity.class);
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

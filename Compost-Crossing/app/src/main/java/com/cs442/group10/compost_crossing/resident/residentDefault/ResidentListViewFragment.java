package com.cs442.group10.compost_crossing.resident.residentDefault;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResidentListViewFragment extends Fragment {

    private static final String AD_TABLE = "adDetails";
    private static final String ID_COL = "id";
    private static final String RESIDENT_REG_TABLE = "residentRegisteration";
    private static final String TITLE_COL = "title";
    private static final String WEIGHT_COL = "weight";
    private static final String BUYER_ID_COL = "buyerId";
    private static final String COST_COL = "cost";
    private static final String BUYER_NAME_COL = "buyerName";
    private static final String ADDRESS_COL = "address";
    private static final String CITY_COL = "city";
    private static final String ZIP_CODE_COL = "zipCode";
    private static final String STATE_COL = "state";
    private static final String SOLD_COL = "sold";

    int imageId = 0;
    List<AdDetail> adDetailList = new ArrayList<>();

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
        final RelativeLayout loadingLayout = (RelativeLayout) view.findViewById(R.id.loadingPanel);

        ResidentListViewAdapter residentListViewAdapter = new ResidentListViewAdapter(getContext(), R.layout.resident_item_list, getActivity(), adDetailList);
        listView.setAdapter(residentListViewAdapter);
        //listView.setEmptyView(emptyTextView);
        residentListViewAdapter.notifyDataSetChanged();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(RESIDENT_REG_TABLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingLayout.setVisibility(View.GONE);
                Map<String, Map<String,Object>> residentRegMap = (Map<String, Map<String,Object>>) dataSnapshot.getValue();
                for(Map.Entry<String, Map<String,Object>> residentMap: residentRegMap.entrySet()) {
                    Map<String, Object> residentRecordMap = residentMap.getValue();
                    String residentId = residentMap.getKey();
                    if (!residentRecordMap.get("adlist").equals(" ") && (residentId.equals(Constants.residentId))) {
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
                            String sold = adDetailsMap.get(SOLD_COL);
                            String buyerName =  adDetailsMap.get(BUYER_NAME_COL);
                            String state = adDetailsMap.get(STATE_COL);

                            final AdDetail adDetail = new AdDetail(id, ownerName, ownerPhone, title, address, city, state, zipCode, cost, sold, weight, buyerId, buyerName, imageId);

                            imageId = setRandomImageId(adDetail, imageId);

                                if (sold.equals("false")) {
                                    adDetailList.add(adDetail);
                                    listView.setBackgroundResource(0);
                                }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error retrieving data in ResidentListView");
            }
        });

        if(adDetailList.size() == 0 && loadingLayout.getVisibility() == View.VISIBLE){
            listView.setBackgroundResource(R.drawable.empty_trash);
        }

        return view;
    }

    private int setRandomImageId(AdDetail adDetail, int imageId) {
        switch (imageId%10){
            case 0 : adDetail.setImageId(R.drawable.compost_1); break;
            case 1 : adDetail.setImageId(R.drawable.compost_2); break;
            case 2 : adDetail.setImageId(R.drawable.compost_3); break;
            case 3 : adDetail.setImageId(R.drawable.compost_4); break;
            case 4 : adDetail.setImageId(R.drawable.compost_5); break;
            case 5 : adDetail.setImageId(R.drawable.compost_6); break;
            case 6 : adDetail.setImageId(R.drawable.compost_7); break;
            case 7 : adDetail.setImageId(R.drawable.compost_8); break;
            case 8 : adDetail.setImageId(R.drawable.compost_9); break;
            case 9 : adDetail.setImageId(R.drawable.compost_10); break;
            default : adDetail.setImageId(R.drawable.compost_11);
        }
        imageId ++;
        return imageId;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

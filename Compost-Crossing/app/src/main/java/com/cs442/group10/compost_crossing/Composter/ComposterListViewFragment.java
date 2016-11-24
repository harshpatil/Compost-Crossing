package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.DB.DbMain;
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

/**
 * {@link ComposterListViewFragment} - Fragment to show composter ad list.
 * @author Chethan
 */
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
    public ListView listView;

    List<AdDetail> compostAdDetailList = new ArrayList<>();
    Map<String, String> compostAdsMap = new HashMap<String,String>();
    Map<String, AdDetail> adDetailMap = new HashMap<String, AdDetail>();
    int imageId = 0;

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
        final View view = inflater.inflate(R.layout.fragment_composter_list_view, container, false);
        final ComposterListViewFragment composterListViewFragment = (ComposterListViewFragment) getFragmentManager().findFragmentById(R.id.composterListViewFragment);
        listView = (ListView) view.findViewById(R.id.composterItemListView);
        final TextView emptyTextView = (TextView) view.findViewById(R.id.emptyAdListForComposter);
        final RelativeLayout loadingLayout = (RelativeLayout) view.findViewById(R.id.loadingPanel);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(RESIDENT_REG_TABLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity() != null) {
                    loadingLayout.setVisibility(View.GONE);

                    if(compostAdDetailList.size() == 0){
                        composterListViewFragment.listView.setBackgroundResource(R.drawable.empty_trash);
                    }

                    compostAdsMap = new HashMap<String, String>();

                    Map<String, Map<String,Object>> residentRegMap = (Map<String, Map<String,Object>>) dataSnapshot.getValue();
                    for(Map.Entry<String, Map<String,Object>> residentMap: residentRegMap.entrySet()) {

                        Map<String, Object> residentRecordMap = residentMap.getValue();

                        if (!residentRecordMap.get("adlist").equals(" ")) {
                            Map<String, Map<String, String>> compostAdListMap = (Map<String, Map<String, String>>) residentRecordMap.get("adlist");

                            for (Map.Entry<String, Map<String, String>> compostAdMap : compostAdListMap.entrySet()) {

                                Map<String, String> adDetailsMap = compostAdMap.getValue();
                                Log.i("SOLDVALUE",adDetailsMap.get(SOLD_COL));

                                Boolean condition = adDetailsMap.get(SOLD_COL).contains("false");

                                Bundle bundle = getActivity().getIntent().getExtras();
                                if (bundle != null) {
                                    Button residentNearByButton = (Button) view.findViewById(R.id.btnResidentNearBy);
                                    Button residentAllButton = (Button) view.findViewById(R.id.btnAllResident);

                                    if (bundle.getBoolean("showNearby")) {
                                        String adDetailsZipCode = String.valueOf(adDetailsMap.get("zipCode")).substring(0, 3);
                                        DbMain dbMain = new DbMain(getActivity().getApplicationContext());
                                        String composterZipCode = dbMain.getComposterZipCode();
                                        if (residentAllButton.getVisibility() == View.GONE) {
                                            residentAllButton.setVisibility(View.VISIBLE);
                                            residentNearByButton.setVisibility(View.GONE);
                                        }
                                        condition = condition && (composterZipCode.startsWith(adDetailsZipCode));
                                    } else {
                                        if (residentNearByButton.getVisibility() == View.GONE) {
                                            residentNearByButton.setVisibility(View.VISIBLE);
                                            residentAllButton.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                if (condition) {
                                    String weight = adDetailsMap.get(WEIGHT_COL);
                                    String title = adDetailsMap.get(TITLE_COL);
                                    String id = adDetailsMap.get(ID_COL);
                                    String address = adDetailsMap.get(ADDRESS_COL);
                                    String city = adDetailsMap.get(CITY_COL);
                                    String zipCode = adDetailsMap.get(ZIP_CODE_COL);
                                    String state = adDetailsMap.get(STATE_COL);
                                    String ownerName = (String) residentRecordMap.get("name");
                                    String ownerPhone = (String) residentRecordMap.get("phone");
                                    String cost = adDetailsMap.get(COST_COL);
                                    String sold = adDetailsMap.get(SOLD_COL);
                                    String buyerName = adDetailsMap.get(BUYER_NAME_COL);

                                    final AdDetail adDetail = new AdDetail(id, ownerName, ownerPhone, title, address, city, state, zipCode, cost, weight, buyerName, imageId);
                                    imageId = setRandomImageId(adDetail, imageId);

                                    compostAdDetailList.add(adDetail);

                                    ComposterListViewAdapter composterListViewAdapter = new ComposterListViewAdapter(getActivity().getApplicationContext(), R.layout.composter_item_list, getActivity(), compostAdDetailList);
                                    listView.setAdapter(composterListViewAdapter);
                                    composterListViewAdapter.notifyDataSetChanged();

                                    final CompostDetailViewFragment detailViewFragment = (CompostDetailViewFragment) getFragmentManager().findFragmentById(R.id.compostDetailViewContainer);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                                            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT && detailViewFragment!= null){
                                                detailViewFragment.adDetail = compostAdDetailList.get(position);
                                                detailViewFragment.setCompostDetailViewValues();
                                            } else {
                                                Intent compostDetailIntent = new Intent(getActivity().getApplicationContext(), CompostDetailActivity.class);
                                                Bundle compostAdBundle = new Bundle();
                                                compostAdBundle.putSerializable("compostAd", compostAdDetailList.get(position));
                                                compostDetailIntent.putExtras(compostAdBundle);
                                                startActivity(compostDetailIntent);
                                            }
                                        }
                                    });
                                }
                            }
                            if(compostAdDetailList.size() > 0){
                                CompostDetailViewFragment detailViewFragment = (CompostDetailViewFragment) getFragmentManager().findFragmentById(R.id.compostDetailViewContainer);
                                composterListViewFragment.listView.setBackgroundResource(0);
                                if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT && detailViewFragment != null){
                                    detailViewFragment.adDetail = compostAdDetailList.get(0);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Compost Crossing", "Firebase --> Error retrieving data in ComposterListView");
            }
        });

        return view;
    }

    /**
     * Method to set random image for compost ads.
     * @param adDetail
     * @param imageId
     * @return int - Random image id.
     */
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

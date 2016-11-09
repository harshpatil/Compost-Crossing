package com.cs442.group10.compost_crossing.resident.historyPage;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    Ads ads;
    int imageId = 0;
    RelativeLayout loadingLayout;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_ads_history);

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanelResidentHistoryPage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("adDetails");
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loadingLayout.setVisibility(View.GONE);
                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();

                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){

                    Map<String,String> compostAdMap = compostAdListMap.getValue();

                    if(String.valueOf(compostAdMap.get("sold")).contains("true")){

                        ads = new Ads();
//                        String buyerId = String.valueOf(compostAdMap.get("buyerId"));
//                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
//                        DatabaseReference ref2 = database2.getReference("composterRegisteration/"+buyerId + "/name");
//                        ref2.push();
//                        ref2.addValueEventListener(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                buyerName = dataSnapshot.getValue(String.class);
//                                ads.setBuyerName(buyerName);
//                                Log.i("BUYERNAME:", buyerName);
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });

                        Log.i("ADSLIST",compostAdMap.get("title"));
                        Log.i("ADSLIST",compostAdMap.get("sold"));

                        switch (imageId%10){
                            case 0 : ads.setImageId(R.drawable.compost_1); break;
                            case 1 : ads.setImageId(R.drawable.compost_2); break;
                            case 2 : ads.setImageId(R.drawable.compost_3); break;
                            case 3 : ads.setImageId(R.drawable.compost_4); break;
                            case 4 : ads.setImageId(R.drawable.compost_5); break;
                            case 5 : ads.setImageId(R.drawable.compost_6); break;
                            case 6 : ads.setImageId(R.drawable.compost_7); break;
                            case 7 : ads.setImageId(R.drawable.compost_8); break;
                            case 8 : ads.setImageId(R.drawable.compost_9); break;
                            case 9 : ads.setImageId(R.drawable.compost_10); break;
                            default : ads.setImageId(R.drawable.compost_11);
                        }

                        imageId ++;

                        ads.setTitle(String.valueOf(compostAdMap.get("title")));
                        ads.setCost(String.valueOf(compostAdMap.get("cost")));
                        ads.setWeight(String.valueOf(compostAdMap.get("weight")));
                        ads.setSold(String.valueOf(compostAdMap.get("sold")));
                        ads.setBuyerName(String.valueOf(compostAdMap.get("buyerName")));

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

        // Naviagtion Code
        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[2];
        drawerList[0] = " Home";
        drawerList[1] = " News Article";


        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_item, drawerList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_audiotrack, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
            // getActionBar().setTitle("Ta-Helper");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            // getActionBar().setTitle("Ta-Helper Shortcuts");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void onClickingBackButton(){

        Intent intent = new Intent(this, ResidentListViewActivity.class);
        startActivity(intent);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if(position==0){
            Intent loginscreen=new Intent(this,MainActivity.class);
            startActivity(loginscreen);
        }else if(position==1){
            Intent loginscreen=new Intent(this,Article.class);
            startActivity(loginscreen);
        }
    }
}

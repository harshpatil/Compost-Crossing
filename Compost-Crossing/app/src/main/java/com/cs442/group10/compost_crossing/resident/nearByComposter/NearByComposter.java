package com.cs442.group10.compost_crossing.resident.nearByComposter;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.resident.createAd.AdCreation;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentAdsHistory;
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
    RelativeLayout loadingLayout;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_by_composter_activity);

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanelNearByComposterPage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("composterRegisteration");
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loadingLayout.setVisibility(View.GONE);
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

        // Naviagtion Code
        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[5];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.YOUR_ACTIVE_ADS;
        drawerList[3] = Constants.YOUR_PAST_ADS;
        drawerList[4] = Constants.CREATE_AD;


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

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if(position==1){

            Intent intent=new Intent(this, Article.class);
            startActivity(intent);

        } else if(position == 2){

            Intent intent = new Intent(this, ResidentListViewActivity.class);
            startActivity(intent);

        } else if(position == 3){

            Intent intent = new Intent(this, ResidentAdsHistory.class);
            startActivity(intent);

        } else if(position == 4){

            Intent intent = new Intent(this, AdCreation.class);
            startActivity(intent);

        }
    }
}

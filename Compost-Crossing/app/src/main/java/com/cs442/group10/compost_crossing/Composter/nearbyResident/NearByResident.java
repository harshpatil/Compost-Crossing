package com.cs442.group10.compost_crossing.Composter.nearbyResident;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity;
import com.cs442.group10.compost_crossing.Composter.historyPage.ComposterAdsHistory;
import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HarshPatil on 10/30/16.
 */
public class NearByResident extends AppCompatActivity {

    Button backButton;
    Resident composter;
    List<Resident> composterList = new ArrayList<Resident>();
    NearByResidentAdapter nearByComposterAdapter;
    ListView nearByComposterListView;
//    String currentUserZipCode = Constants.composterZip;
    String currentUserZipCode;
    private static final int SHOW_PREFERENCES = 0;
    int imageId = 0;
    DbMain dbMain;
    RelativeLayout loadingLayout;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_by_resident);

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanelNearByComposterPage);

        dbMain = new DbMain(this);
        currentUserZipCode = dbMain.getComposterZipCode();
        Log.i("ComposterZIPCODE", currentUserZipCode);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("residentRegisteration");
        ref.push();

        FragmentManager fragmentManager = getFragmentManager();
        final NearByResidentFragment nearByComposterFragment = (NearByResidentFragment) fragmentManager.findFragmentById(R.id.ComposterNearByResidentFragment);
        nearByComposterAdapter = new NearByResidentAdapter(this, R.layout.composter_nearby_resident_fragment, composterList);
        nearByComposterFragment.setListAdapter(nearByComposterAdapter);
        nearByComposterListView = nearByComposterFragment.getListView();
        nearByComposterAdapter.notifyDataSetChanged();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loadingLayout.setVisibility(View.GONE);
                Map<String, Map<String, Object>> compostInfoMap = (Map<String, Map<String, Object>>) dataSnapshot.getValue();

                for (Map.Entry<String, Map<String, Object>> compostAdListMap : compostInfoMap.entrySet()) {

                    Map<String, Object> compostAdMap = compostAdListMap.getValue();
                    //Map<String, Object> ss = compostAdListMap.getValue();
                    String composterZip = String.valueOf(compostAdMap.get("zipcode")).substring(0, 3);
                    String currentUserPin = "";
                    if (composterZip.length() >= Constants.ZIP_CODE_MIN_LENGTH) {
                        currentUserPin = currentUserZipCode.substring(0, 3);
                    }
                    Log.i("ComposterZIPCODE", composterZip);
                    Log.i("ComposterZIPCODE", currentUserPin);

                    if (composterZip.equals(currentUserPin)) {

                        if (!compostAdMap.get("adlist").equals(" ")) {
                            Map<String, Map<String, String>> ad = (Map<String, Map<String, String>>) compostAdMap.get("adlist");
                            for (Map.Entry<String, Map<String, String>> inad : ad.entrySet()) {
                                Map<String, String> adDetailsMap = inad.getValue();
                                if (adDetailsMap.get("sold").contains("false")) {
                                    composter = new Resident();
                                    composter.setTitle(String.valueOf(adDetailsMap.get("title")));
                                    composter.setWeight(String.valueOf(adDetailsMap.get("weight")));
                                    composter.setCost(String.valueOf(adDetailsMap.get("cost")));
                                    switch (imageId % 10) {
                                        case 0:
                                            composter.setImageId(R.drawable.company_1);
                                            break;
                                        case 1:
                                            composter.setImageId(R.drawable.company_2);
                                            break;
                                        case 2:
                                            composter.setImageId(R.drawable.company_3);
                                            break;
                                        case 3:
                                            composter.setImageId(R.drawable.company_4);
                                            break;
                                        case 4:
                                            composter.setImageId(R.drawable.company_5);
                                            break;
                                        case 5:
                                            composter.setImageId(R.drawable.company_6);
                                            break;
                                        case 6:
                                            composter.setImageId(R.drawable.company_7);
                                            break;
                                        case 7:
                                            composter.setImageId(R.drawable.company_8);
                                            break;
                                        case 8:
                                            composter.setImageId(R.drawable.company_9);
                                            break;
                                        default:
                                            composter.setImageId(R.drawable.company_10);
                                    }
                                    imageId++;
                                    composterList.add(composter);
                                }
                            }
                        }
                        nearByComposterFragment.getListView().setBackgroundResource(0);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

        if(composterList.size() == 0 && loadingLayout.getVisibility() == View.VISIBLE){
            nearByComposterFragment.getListView().setBackgroundResource(R.drawable.empty_trash);
        }


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

        drawerList = new String[7];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.COMPOSTER_VIEW_ADS;
        drawerList[3] = Constants.COMPOSTER_VIEW_NEARBY_ADS;
        drawerList[4] = Constants.YOUR_PURCHASE_HISTORY;
        drawerList[5] = Constants.SETTINGS;
        drawerList[6] = Constants.BACK;

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_list_item, drawerList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.drawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.drawer);
        getSupportActionBar().setTitle(R.string.composterViewTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to show Compost List View screen on click of back button in Near by Resident screen
     */
    public void onClickingBackButton(){
        Intent intent = new Intent(this, ComposterListViewActivity.class);
        startActivity(intent);
    }

    /**
     * Item Click Listener to navigate to specific screen.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Show Activity based on item clicked in the drawer
     * @param position
     */
    private void selectItem(int position) {

        if(position==0){

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if(position==1){

            Intent intent=new Intent(this, Article.class);
            startActivity(intent);

        } else if(position==2){

            Intent intent=new Intent(this, ComposterListViewActivity.class);
            startActivity(intent);

        } else if(position == 3){

            Intent intent=new Intent(this, NearByResident.class);
            startActivity(intent);

        } else if(position == 4){

            Intent intent=new Intent(this, ComposterAdsHistory.class);
            startActivity(intent);

        } else if(position == 5){

            Class<?> c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? MyPreferenceActivity.class:MyPreferenceActivity.class;
            Intent i = new Intent(this, c);
            startActivityForResult(i, SHOW_PREFERENCES);

        } else if(position == 6){

            Intent intent = new Intent(this, ComposterListViewActivity.class);
            startActivity(intent);

        }
    }
}

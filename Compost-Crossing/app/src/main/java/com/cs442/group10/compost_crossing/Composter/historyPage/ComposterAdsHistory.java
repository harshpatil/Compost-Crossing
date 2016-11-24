package com.cs442.group10.compost_crossing.Composter.historyPage;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
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

import com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity;
import com.cs442.group10.compost_crossing.Composter.nearbyResident.NearByResident;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.cs442.group10.compost_crossing.resident.createAd.AdCreation;
import com.cs442.group10.compost_crossing.resident.nearByComposter.NearByComposter;
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
public class ComposterAdsHistory extends AppCompatActivity {

    Button backButton;
    List<Ads> adsList = new ArrayList<Ads>();
    ComposterHistoryAdaptor composterHistoryAdapter;
    ListView composterHistoryListView;
    Ads ads;
    int imageId = 0;
    RelativeLayout loadingLayout;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.composter_ads_history);

        FragmentManager fragmentManager = getFragmentManager();
        final CompostorHistoryFragment composterHistoryFragment = (CompostorHistoryFragment) fragmentManager.findFragmentById(R.id.CompostorHistoryFragment);
        composterHistoryAdapter = new ComposterHistoryAdaptor(this, R.layout.composter_history_fragment, adsList);
        composterHistoryFragment.setListAdapter(composterHistoryAdapter);
        composterHistoryListView = composterHistoryFragment.getListView();
        composterHistoryAdapter.notifyDataSetChanged();

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanelComposterHistoryPage);

        String url ="composterRegisteration/"+ Constants.composterId +"/adList";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(url);
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null && dataSnapshot.getValue() != " " && !dataSnapshot.getValue().getClass().equals(String.class)) {

                    loadingLayout.setVisibility(View.GONE);
                Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();

                for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){

                        Map<String, String> compostAdMap = compostAdListMap.getValue();

                        if (String.valueOf(compostAdMap.get("sold")).contains("true")) {

                            ads = new Ads();
                            Log.i("ADSLIST", compostAdMap.get("title"));
                            Log.i("ADSLIST", compostAdMap.get("sold"));

                            switch (imageId % 10) {
                                case 0:
                                    ads.setImageId(R.drawable.compost_11);
                                    break;
                                case 1:
                                    ads.setImageId(R.drawable.compost_10);
                                    break;
                                case 2:
                                    ads.setImageId(R.drawable.compost_9);
                                    break;
                                case 3:
                                    ads.setImageId(R.drawable.compost_8);
                                    break;
                                case 4:
                                    ads.setImageId(R.drawable.compost_7);
                                    break;
                                case 5:
                                    ads.setImageId(R.drawable.compost_6);
                                    break;
                                case 6:
                                    ads.setImageId(R.drawable.compost_5);
                                    break;
                                case 7:
                                    ads.setImageId(R.drawable.compost_4);
                                    break;
                                case 8:
                                    ads.setImageId(R.drawable.compost_3);
                                    break;
                                case 9:
                                    ads.setImageId(R.drawable.compost_2);
                                    break;
                                default:
                                    ads.setImageId(R.drawable.compost_1);
                            }

                            imageId++;

                            ads.setTitle(String.valueOf(compostAdMap.get("title")));
                            ads.setCost(String.valueOf(compostAdMap.get("cost")));
                            ads.setWeight(String.valueOf(compostAdMap.get("weight")));
                            ads.setSold(String.valueOf(compostAdMap.get("sold")));
                            ads.setBuyerName(String.valueOf(compostAdMap.get("buyerName")));

                            adsList.add(ads);
                            composterHistoryFragment.getListView().setBackgroundResource(0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });
        if(adsList.size() == 0 && loadingLayout.getVisibility() == View.VISIBLE){
            composterHistoryFragment.getListView().setBackgroundResource(R.drawable.empty_trash);
            loadingLayout.setVisibility(View.GONE);
        }

        Log.i("ADSLIST", String.valueOf(adsList.size()));



        backButton = (Button) findViewById(R.id.backButtonComposterHistoryPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingBackButton();
            }
        });

        // Naviagtion Code
        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[8];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.COMPOSTER_VIEW_ADS;
        drawerList[3] = Constants.COMPOSTER_VIEW_NEARBY_ADS;
        drawerList[4] = Constants.YOUR_PURCHASE_HISTORY;
        drawerList[5] = Constants.SETTINGS;
        drawerList[6] = Constants.BACK;
        drawerList[7] = Constants.SIGNOUT;

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_list_item, drawerList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_audiotrack, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void onClickingBackButton(){

        Intent intent = new Intent(this, ComposterListViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
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
        else if(position == 7){

            Constants.loginflag=0;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }
}

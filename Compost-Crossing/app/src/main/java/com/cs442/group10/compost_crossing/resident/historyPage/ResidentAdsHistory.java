package com.cs442.group10.compost_crossing.resident.historyPage;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.cs442.group10.compost_crossing.resident.createAd.AdCreation;
import com.cs442.group10.compost_crossing.resident.nearByComposter.NearByComposter;
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
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
    DbMain dbMain;
    String phoneNumber;
    ResidentHistoryFragment residentHistoryFragment;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_ads_history);

        dbMain = new DbMain(this);
        phoneNumber = dbMain.getResidentPhoneNumber();
        Log.i("PHONENUMBER", phoneNumber);

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanelResidentHistoryPage);

        FragmentManager fragmentManager = getFragmentManager();
        residentHistoryFragment = (ResidentHistoryFragment) fragmentManager.findFragmentById(R.id.ResidentHistoryFragment);
        residentHistoryAdapter = new ResidentHistoryAdapter(this, R.layout.resident_history_fragment, adsList);
        residentHistoryFragment.setListAdapter(residentHistoryAdapter);
        residentHistoryListView = residentHistoryFragment.getListView();
        residentHistoryAdapter.notifyDataSetChanged();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("residentRegisteration/" + phoneNumber + "/adlist" );
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loadingLayout.setVisibility(View.GONE);

                if(residentHistoryFragment.isVisible() && dataSnapshot.getValue() != null && dataSnapshot.getValue() != " " && !dataSnapshot.getValue().getClass().equals(String.class)) {

                    Map<String, Map<String,String>> compostInfoMap = (Map<String, Map<String,String>>) dataSnapshot.getValue();

                    for(Map.Entry<String, Map<String,String>> compostAdListMap: compostInfoMap.entrySet()){

                        Map<String,String> compostAdMap = compostAdListMap.getValue();

                        if(String.valueOf(compostAdMap.get("sold")).contains("true")){

                            ads = new Ads();
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
                            residentHistoryFragment.getListView().setBackgroundResource(0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

        Log.i("ADSLIST", String.valueOf(adsList.size()));

        if(adsList.size() == 0 && loadingLayout.getVisibility() == View.VISIBLE){
            residentHistoryFragment.getListView().setBackgroundResource(R.drawable.empty_trash);
        }

        backButton = (Button) findViewById(R.id.backButtonResidentHistoryPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingBackButton();
            }
        });

        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[9];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.YOUR_ACTIVE_ADS;
        drawerList[3] = Constants.YOUR_PAST_ADS;
        drawerList[4] = Constants.CREATE_AD;
        drawerList[5] = Constants.NEARBY_COMPOSTERS;
        drawerList[6] = Constants.SETTINGS;
        drawerList[7] = Constants.BACK;
        drawerList[8] = Constants.SIGNOUT;

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
        getSupportActionBar().setTitle(R.string.residentViewTitle);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.resident.ResidentAdsHistory", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("residentHistoryFirstrun", true);
        if (isFirstRun) {

            sharedPreferences.edit().putBoolean("residentHistoryFirstrun", false).commit();
            showFirstShowCase();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to show Resident Ad List on click of back button.
     */
    public void onClickingBackButton(){

        Intent intent = new Intent(this, ResidentListViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
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

        } else if(position == 2){

            Intent intent = new Intent(this, ResidentListViewActivity.class);
            startActivity(intent);

        } else if(position == 3){

            Intent intent = new Intent(this, ResidentAdsHistory.class);
            startActivity(intent);

        } else if(position == 4){

            Intent intent = new Intent(this, AdCreation.class);
            startActivity(intent);

        } else if(position == 5){

            Intent intent = new Intent(this, NearByComposter.class);
            startActivity(intent);

        } else if(position == 6){

            Class<?> c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? MyPreferenceActivity.class:MyPreferenceActivity.class;
            Intent i = new Intent(this, c);
            startActivityForResult(i, SHOW_PREFERENCES);

        } else if(position == 7){

            Intent intent = new Intent(this, ResidentListViewActivity.class);
            startActivity(intent);

        }
        else if(position == 8){

            Constants.loginflagforresident=0;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    private void showFirstShowCase(){
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(mDrawerLayout))
                .hideOnTouchOutside()
                .setContentTitle("Here you will see list of your sold ads")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        showSecondShowCase();
                    }

                })
                .build();
    }

    private void showSecondShowCase() {
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(backButton))
                .hideOnTouchOutside()
                .setContentTitle("Click here to go back to Resident dashboard")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                    }

                })
                .build();
    }

}

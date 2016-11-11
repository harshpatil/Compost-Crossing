package com.cs442.group10.compost_crossing.resident.residentDefault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.cs442.group10.compost_crossing.resident.createAd.AdCreation;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentAdsHistory;
import com.cs442.group10.compost_crossing.resident.nearByComposter.NearByComposter;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class ResidentListViewActivity extends AppCompatActivity {

    Button history;
    Button nearByComposter;
    Button postNewAd;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_list_view);

        history = (Button) findViewById(R.id.btnResidentHistory);
        nearByComposter = (Button) findViewById(R.id.btnComposterNearBy);
        postNewAd = (Button) findViewById(R.id.btnPostAd);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistoryButton();
            }
        });

        nearByComposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNearByComposterButton();
            }
        });

        postNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingPostNewAd();
            }
        });

        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[8];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.YOUR_ACTIVE_ADS;
        drawerList[3] = Constants.YOUR_PAST_ADS;
        drawerList[4] = Constants.CREATE_AD;
        drawerList[5] = Constants.NEARBY_COMPOSTERS;
        drawerList[6] = Constants.SETTINGS;
        drawerList[7] = Constants.BACK;

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

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.residentDefault.ResidentListViewActivity", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("residentFirstrun", true);
        if (isFirstRun) {

            sharedPreferences.edit().putBoolean("residentFirstrun", false).commit();
            showFirstShowCase();
        }
    }

    public void back(View v){
        onBackPressed();
    }

    public void navigateToNewAdForm(View v){
    }

    public void onClickHistoryButton(){

        Intent intent = new Intent(this, ResidentAdsHistory.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }

    public void onClickNearByComposterButton(){

        Intent intent = new Intent(this, NearByComposter.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void onClickingPostNewAd(){

        Intent intent = new Intent(this, AdCreation.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
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

    }

    private void showFirstShowCase(){
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(history))
                .hideOnTouchOutside()
                .setContentTitle("Click here to check your past Ads")
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
                .setTarget(new ViewTarget(postNewAd))
                .hideOnTouchOutside()
                .setContentTitle("Click here to create new Ad")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        showThirdShowCase();
                    }

                })
                .build();
    }

    private void showThirdShowCase() {
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)//moduleListView.getChildAt(0).findViewById(R.id.editModuleButtonView))
                .setTarget(new ViewTarget(nearByComposter))
                .hideOnTouchOutside()
                .setContentTitle("Click here to see nearby composters")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        showFourthShowCase();
                    }

                })
                .build();
    }

    private void showFourthShowCase() {
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(mDrawerLayout))
                .hideOnTouchOutside()
                .setContentTitle("Slide from left to navigate between screens")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        //showFifthShowCase();
                    }
                }).build();
    }

}

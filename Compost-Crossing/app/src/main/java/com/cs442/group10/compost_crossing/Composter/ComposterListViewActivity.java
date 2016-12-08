package com.cs442.group10.compost_crossing.Composter;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.Composter.historyPage.ComposterAdsHistory;
import com.cs442.group10.compost_crossing.Composter.nearbyResident.NearByResident;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

/**
 * {@link ComposterListViewActivity} - List view activity for Composter Ad lookup.
 * @author Chethan
 */
public class ComposterListViewActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private String[] drawerList;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;
    ListView listView;
    Button history;
    Button nearByResident;
    Button allResidentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_list_view);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

       FragmentManager fragmentManager = getSupportFragmentManager();
        ComposterListViewFragment fragment = (ComposterListViewFragment) fragmentManager.findFragmentById(R.id.composterListViewFragment);
        listView = fragment.listView;

        history = (Button) findViewById(R.id.btnComposterHistory);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistoryButton();
            }
        });

        nearByResident = (Button) findViewById(R.id.btnResidentNearBy);

        nearByResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNearByResidentButton();
            }
        });

        allResidentBtn = (Button) findViewById(R.id.btnAllResident);

        allResidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAllResidentButton();
            }
        });
        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[9];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.COMPOSTER_VIEW_ADS;
        drawerList[3] = Constants.COMPOSTER_VIEW_NEARBY_ADS;
        drawerList[4] = Constants.YOUR_PURCHASE_HISTORY;
        drawerList[5] = Constants.SETTINGS;
        drawerList[6] = Constants.BACK;
        drawerList[7] = Constants.SIGNOUT;
        drawerList[8] = Constants.HELP;

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

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("firstrun", true);
        if (isFirstRun) {

            sharedPreferences.edit().putBoolean("firstrun", false).commit();
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

    @Override
    public void onBackPressed() {
        navigateToMainActivity();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        } else if(position == 7){

            Constants.loginflag=0;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if(position == 8){

            SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("firstrun", true).commit();
            Intent intent = new Intent(this, ComposterListViewActivity.class);
            startActivity(intent);

        }
    }

    /**
     * Method to show Ad History on click of History button.
     */
    public void onClickHistoryButton(){

        Intent intent = new Intent(this, ComposterAdsHistory.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }

    /**
     * Method to Filter Near by Resident Ads to the Composter.
     */
    public void onClickNearByResidentButton(){

        Intent intent = new Intent(this, ComposterListViewActivity.class);
        intent.putExtra("showNearby", true);
        startActivity(intent);
    }

    /**
     * Method to show all the Resident Ads to the Composter.
     */
    public void onClickAllResidentButton(){
        Intent intent = new Intent(this, ComposterListViewActivity.class);
        startActivity(intent);
    }

    private void showFirstShowCase(){
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(mDrawerLayout))
                .hideOnTouchOutside()
                .setContentTitle("Slide from left to navigate between screens")
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
                .setStyle(R.style.CustomShowcaseTheme2)//
                .setTarget(new ViewTarget(history))
                .hideOnTouchOutside()
                .setContentTitle("Click here to view your purchase history")
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
                .setStyle(R.style.CustomShowcaseTheme2)//
                .setTarget(new ViewTarget(nearByResident))
                .hideOnTouchOutside()
                .setContentTitle("Click here to view nearby Ads")
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
                .setStyle(R.style.CustomShowcaseTheme2)//
                .setTarget(new ViewTarget(listView))
                .hideOnTouchOutside()
                .setContentTitle("Click on any list to view it's details")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                    }

                })
                .build();
    }
}

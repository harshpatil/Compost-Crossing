package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.Composter.historyPage.ComposterAdsHistory;
import com.cs442.group10.compost_crossing.Composter.nearbyResident.NearByResident;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;

/**
 * {@link CompostDetailActivity} - Activity to display compost ad details
 * @author Chethan
 */
public class CompostDetailActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compost_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AdDetail adDetail = (AdDetail) bundle.getSerializable("compostAd");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {//For Landscape mode
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
            } else {//for Portrait mode
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
            }
            fragmentTransaction.commit();

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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.drawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.drawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to navigate back to Composter Ad List screen.
     * @param view
     */
    public void navigateBackToComposterAdsListView(View view){
        onBackPressed();
    }

    public void backToAdPage(View view) {
        navigateToComposterListViewActivity();
    }

    @Override
    public void onBackPressed() {
        navigateToComposterListViewActivity();
    }

    /**
     * Method to start Composter List View Activity.
     */
    private void navigateToComposterListViewActivity() {
        Intent composterListViewIntent = new Intent(this, ComposterListViewActivity.class);
        startActivity(composterListViewIntent);
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

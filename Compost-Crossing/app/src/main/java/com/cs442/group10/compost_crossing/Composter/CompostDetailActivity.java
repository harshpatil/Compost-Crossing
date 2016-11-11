package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;

public class CompostDetailActivity extends FragmentActivity {

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
                //fragmentTransaction.replace(R.id.compostDetailViewFragment, new CompostDetailViewFragment()); // list view fragment
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
            } else {//for Portrait mode
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
                //fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();

        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[6];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.COMPOSTER_VIEW_ADS;
        drawerList[3] = Constants.COMPOSTER_VIEW_NEARBY_ADS;
        drawerList[4] = Constants.SETTINGS;
        drawerList[5] = Constants.BACK;

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

    public void navigateBackToComposterAdsListView(View view){
        onBackPressed();
    }

    public void backToAdPage(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
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

//            Intent intent=new Intent(this, ComposterListViewActivity.class);
//            startActivity(intent);

        } else if(position == 4){

            Class<?> c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? MyPreferenceActivity.class:MyPreferenceActivity.class;
            Intent i = new Intent(this, c);
            startActivityForResult(i, SHOW_PREFERENCES);

        } else if(position == 5){

            Intent intent = new Intent(this, ComposterListViewActivity.class);
            startActivity(intent);

        }
    }

}

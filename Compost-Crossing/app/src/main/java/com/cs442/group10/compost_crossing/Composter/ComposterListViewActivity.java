package com.cs442.group10.compost_crossing.Composter;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentHistoryAdapter;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentHistoryFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class ComposterListViewActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_list_view);

        FragmentManager fragmentManager = getFragmentManager();
        ComposterListViewFragment fragment = (ComposterListViewFragment) fragmentManager.findFragmentById(R.id.composterListViewFragment);
        listView = fragment.listView;

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

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("firstrun", true);
        if (isFirstRun) {

            sharedPreferences.edit().putBoolean("firstrun", false).commit();
            showFirstShowCase();
        }

    }

    public void onBackCompostListView(View view){
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
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
                .setTarget(new ViewTarget(listView))
                .hideOnTouchOutside()
                .setContentTitle("Click on any list to view details")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
//                        showFourthShowCase();
                    }

                })
                .build();
    }

}

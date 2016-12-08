package com.cs442.group10.compost_crossing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.Composter.ComposterListViewActivity;
import com.cs442.group10.compost_crossing.Composter.ComposterRegistration;
import com.cs442.group10.compost_crossing.Composter.composter_login;
import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.newsArticle.MyAlarm;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.cs442.group10.compost_crossing.resident.ResidentLogin;
import com.cs442.group10.compost_crossing.resident.ResidentRegisteration;
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button readArticle;
    Button residentButton;
    Button composterButton;
    DbMain db;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        db = new DbMain(this);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        startAlarmService();

        readArticle = (Button) findViewById(R.id.readArticleButton);
        readArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingReadArticleButton();
            }
        });

        composterButton = (Button) findViewById(R.id.compostButton);
        composterButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                int count = db.numberOfDataComposter();

                if (count >= 1 && Constants.loginflag == 0) {
                    db.setComposterUserDetails();//Sets the composter name,id and zip - Chethan
                    Intent composterListViewIntent = new Intent(getBaseContext(), composter_login.class);
                    startActivity(composterListViewIntent);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else if (count >= 1 && Constants.loginflag == 1) {
                    db.setComposterUserDetails();//Sets the composter name,id and zip - Chethan
                    Intent composterListViewIntent = new Intent(getBaseContext(), ComposterListViewActivity.class);
                    startActivity(composterListViewIntent);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                }
                else{
                    Intent composterregistration = new Intent(getApplicationContext(), ComposterRegistration.class);
                    startActivity(composterregistration);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                }
            }
        });

        residentButton = (Button) findViewById(R.id.residentButton);
        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = db.numberOfDataResident();
                String id = db.getResidentID();
                Constants.residentId = id;

                if (count >= 1 && Constants.loginflagforresident == 0) {
                    Intent composterListViewIntent = new Intent(getBaseContext(), ResidentLogin.class);
                    startActivity(composterListViewIntent);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else if (count >= 1 && Constants.loginflagforresident == 1) {
                    Intent residentListViewIntent = new Intent(getBaseContext(), ResidentListViewActivity.class);
                    startActivity(residentListViewIntent);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else {
                    Intent residentregistration = new Intent(getApplicationContext(), ResidentRegisteration.class);
                    startActivity(residentregistration);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                }
            }
        });

        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[4];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.SETTINGS;
        drawerList[3] = Constants.HELP;


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

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.MainActivity", MODE_PRIVATE);
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

    /**
     * Method to show Compost article screen.
     */
    public void onClickingReadArticleButton() {

        Intent readArticleIntent = new Intent(this, Article.class);
        startActivity(readArticleIntent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void startAlarmService() {

        Date date = new Date();
        Calendar calendarAlarm = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(date);
        calendarAlarm.setTime(date);
        calendarAlarm.set(Calendar.HOUR_OF_DAY, 8);
        calendarAlarm.set(Calendar.MINUTE, 0);
        calendarAlarm.set(Calendar.SECOND, 0);
        if (calendarAlarm.before(calendarNow)) {
            calendarAlarm.add(Calendar.DATE, 1);
        }

        Log.i("MainActivity", "Starting Alarm");
        Intent alarmIntent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarAlarm.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
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

        if (position == 0) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (position == 1) {

            Intent intent = new Intent(this, Article.class);
            startActivity(intent);

        } else if (position == 2) {

            Class<?> c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? MyPreferenceActivity.class : MyPreferenceActivity.class;
            Intent i = new Intent(this, c);
            startActivityForResult(i, SHOW_PREFERENCES);

        } else if(position == 3){

            SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.MainActivity", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("firstrun", true).commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void showFirstShowCase(){
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(composterButton))
                .hideOnTouchOutside()
                .setContentTitle("Click this button if you are a Composter")
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
                .setTarget(new ViewTarget(residentButton))
                .hideOnTouchOutside()
                .setContentTitle("Click this button if you are a Resident")
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
                .setTarget(new ViewTarget(readArticle))
                .hideOnTouchOutside()
                .setContentTitle("Click this button to read Compost News Article")
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

                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exitMessage, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
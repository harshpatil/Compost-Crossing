package com.cs442.group10.compost_crossing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.Composter.CompostDetailActivity;

import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.resident.ResidentListViewActivity;
import com.cs442.group10.compost_crossing.newsArticle.ArticleNotificationService;
import com.cs442.group10.compost_crossing.newsArticle.MyAlarm;
import com.cs442.group10.compost_crossing.newsArticle.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ViewListingFragment.OnListingSelectedListener {

    Button readArticle;
    Button residentButton;
    Button composterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        writeArticleToDB();

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

        residentButton = (Button)findViewById(R.id.residentButton);
        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent residentListViewIntent = new Intent(getBaseContext(), ResidentListViewActivity.class);
                startActivity(residentListViewIntent);

            }
        });

        composterButton = (Button) findViewById(R.id.compostButton);
        composterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.screen_2);
                ListView lv = (ListView) findViewById(R.id.expandableListView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, Listings.Names);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//temporary work around to navigate to detail view
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent compostDetailIntent = new Intent(getApplicationContext(),CompostDetailActivity.class);
                        startActivity(compostDetailIntent);
                    }
                });
            }
        });

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ViewListingFragment firstFragment = new ViewListingFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }

    }

    public void onListingSelected(int position) {

        Intent compostDetailIntent = new Intent(this,CompostDetailActivity.class);
        startActivity(compostDetailIntent);
    }


    public void onClickingReadArticleButton(){

        Intent readArticleIntent = new Intent(this, Article.class);
        startActivity(readArticleIntent);
    }

    public void startAlarmService(){

        Date date  = new Date();
        Calendar calendarAlarm = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(date);
        calendarAlarm.setTime(date);
        calendarAlarm.set(Calendar.HOUR_OF_DAY,8);
        calendarAlarm.set(Calendar.MINUTE, 0);
        calendarAlarm.set(Calendar.SECOND,0);
        if(calendarAlarm.before(calendarNow)){
            calendarAlarm.add(Calendar.DATE,1);
        }

        Log.i("MainActivity", " Starting Alarm");
        Intent alarmIntent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarAlarm.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
    }

}
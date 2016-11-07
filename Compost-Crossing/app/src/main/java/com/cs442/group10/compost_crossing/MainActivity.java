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

<<<<<<< HEAD
import com.cs442.group10.compost_crossing.Composter.ComposterRegistration;
=======
>>>>>>> e7b7fda7f628e9c09fac7cfcdb7eb0f2101784c3
import com.cs442.group10.compost_crossing.newsArticle.Article;
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
    DbMain db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
<<<<<<< HEAD
        db = new DbMain(this);
=======

//        writeArticleToDB();

>>>>>>> e7b7fda7f628e9c09fac7cfcdb7eb0f2101784c3
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

        final Button Composter= (Button)findViewById(R.id.compostButton);
        final Button residentButton= (Button)findViewById(R.id.residentButton);

        Composter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD

                int count = db.numberOfentries();
                if(count >= 1){
                    Intent compostDetailIntent = new Intent(getApplicationContext(),CompostDetailActivity.class);
                    startActivity(compostDetailIntent);
                }
                else{
                    Intent composterregistration = new Intent(getApplicationContext(),ComposterRegistration.class);
                    startActivity(composterregistration);
                }
=======
>>>>>>> e7b7fda7f628e9c09fac7cfcdb7eb0f2101784c3
            }
        });

        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            }
        });

        final Button button = (Button) findViewById(R.id.compostButton);
        button.setOnClickListener(new View.OnClickListener() {
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

//    public void writeArticleToDB(){
//
//        String title = "Bagged soils can contain herbicides, gnats, and other unsavory problems.";
//        String body = "Stories about compost possibly contaminated with heavy metals from sewage waste and disastrous herbicides turning up in potting soil aren’t new. In 2010, the University of Maryland Extension put out a “Gardener’s Alert! Beware of Herbicide-Contaminated Compost and Manure.” The Ohio State University Extension put out a fact sheet (PDF) on one persistent pesticide showing up in compost that kills off tomato, eggplant and other nightshade family vegetables as well as beans and sunflower.\n" +
//                "But it seems more recently that gardeners are starting to pay attention to other problems that come with mass-produced, commercial potting soils and compost: the importation of pests and disease into your garden or indoor grow space.\n" +
//                "Potting soil, whether it comes in bags or in pots with nursery stock you buy, is a great contaminator. It’s singled out for importing root aphids, once little known, into greenhouses and gardens across the country in epidemic-like proportions. It also has been known to carry fungus gnats.\n" +
//                "One popular brand of potting soil is so well-known to carry the nuisance of otherwise harmless gnats that Consumer Affairs has a page devoted to complaints.\n" +
//                "The internet also hosts complaints of low-end soils and compost from big chain stores that included plastic and other trash.\n" +
//                "It’s hard to track the spread of plant diseases and fungal diseases in home gardens. But potting soil is highly suspect in the introduction of disease and molds and fungus where it is used. Buy only the best quality from someone you trust.\n" +
//                "Root aphids often arrive in the soil that potted plants are rooted in. These aphids sap strength and vigor from plants, making for inferior fruiting and flowering. Buying clones and nursery stock from trusted, preferably local growers who you can question is a big plus. Avoid the kind of nursery stock sold out front chain supermarkets and big box stores.\n" +
//                "Buying trusted brands from trusted sources is also important when purchasing manures and compost. Any compost made from city lawn clippings and other green waste may contain residual herbicides. The City of Seattle learned a tough lesson back in the 1990s when the compost made of recycled yard waste started killing off vegetable plants. The problem eventually led to the ban of clopyralid for lawn use.\n" +
//                "Now another persistent herbicide, aminopyralid, is turning up in compost. Aminopyralid is widely used in hayfields and pastures to kill off broad leaf weeds. Like clopyralid, it affects a variety of broad leaf vegetable plants, including peas, beans and tomatoes. Also like clopyralid, it can persist in soil and compost for months and even years (the composting process does not speed its decay).\n" +
//                "Amniopyralid, manufactured by Dow AgroSciences, shows up in dairy and cattle manure. That manure is widely reapplied to farms and fields but also makes its way into manures and composts sold to home gardeners.\n" +
//                "Problems with the pesticide first introduced in 2005 began showing up in England by 2008. Dow suspended the use of the spray until it could issue warnings (note Dow’s chart where potato, lettuce, beans, tomato, and “etc.” are crossed out in red).\n" +
//                "Short of being able to buy compost and grow-soil from organic sources, it’s safest to make your owncomposts and potting soils. That way you’ll know exactly what’s going in and what’s not. You can’t always buy peace of mind.\n\nBy E. Vinje\n";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("articlesList");
//        News news = new News();
//        news.setTitle(title);
//        news.setBody(body);
//        myRef.child("news_08112016").setValue(news);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Log.i("DataBase", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//                Log.w("DataBase", "Failed to read value.", error.toException());
//            }
//        });
//    }
}
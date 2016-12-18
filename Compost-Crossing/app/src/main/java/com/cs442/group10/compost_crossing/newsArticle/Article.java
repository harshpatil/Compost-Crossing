package com.cs442.group10.compost_crossing.newsArticle;

import android.app.NotificationManager;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.preferences.MyPreferenceActivity;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HarshPatil on 10/30/16.
 */
public class Article extends AppCompatActivity {

    TextView articleTitle;
    TextView articleBody;
    ImageView image;
    Button goToHomePageButton;
    RelativeLayout loadingLayout;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int SHOW_PREFERENCES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.news_article);

        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanel);

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();
        Log.i("Today's date :", dateFormat.format(date));
//        String articleNumber = dateFormat.format(date);
        String articleNumber = "22112016";
        Log.i("ArticleNumber : ", articleNumber);
        DateFormat format2=new SimpleDateFormat("EEEE");
        String dayOfTheWeek = format2.format(date);
        Log.i("Today is :", dayOfTheWeek);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articlesList/news_"+articleNumber);
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingLayout.setVisibility(View.GONE);
                News news = dataSnapshot.getValue(News.class);
                Log.i("FETCH_FIRBASE", " : Title : " + news.getTitle());
                Log.i("FETCH_FIRBASE", " : Body : " + news.getBody());
                articleTitle.setText(news.getTitle());
                articleBody.setText(news.getBody());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.ARTICLE_NOTIFICATION_ID);

        articleTitle = (TextView) findViewById(R.id.articleTitle);
        articleBody = (TextView) findViewById(R.id.articleBody);
        image = (ImageView)findViewById(R.id.imageArticlePage);

        if(dayOfTheWeek.equals("Monday")){
            image.setImageResource(R.drawable.compost_2);
        }else if(dayOfTheWeek.equals("Tuesday")){
            image.setImageResource(R.drawable.compost_3);
        }else if(dayOfTheWeek.equals("Wednesday")){
            image.setImageResource(R.drawable.compost_4);
        }else if(dayOfTheWeek.equals("Thursday")){
            image.setImageResource(R.drawable.compost_5);
        }else if(dayOfTheWeek.equals("Friday")){
            image.setImageResource(R.drawable.compost_6);
        }else if(dayOfTheWeek.equals("Saturday")){
            image.setImageResource(R.drawable.compost_7);
        }else{
            image.setImageResource(R.drawable.compost_8);
        }

        goToHomePageButton = (Button)findViewById(R.id.backButtonArticlePage);
        goToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingGoToHomePageButton();
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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_audiotrack, R.string.drawer_open, R.string.drawer_close) {

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

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.newsArticle.Article", MODE_PRIVATE);
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

    public void onClickingGoToHomePageButton(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

            Class<?> c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? MyPreferenceActivity.class:MyPreferenceActivity.class;
            Intent i = new Intent(this, c);
            startActivityForResult(i, SHOW_PREFERENCES);

        } else if(position == 3){

            SharedPreferences sharedPreferences = getSharedPreferences("com.cs442.group10.compost_crossing.newsArticle.Article", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("firstrun", true).commit();
            Intent intent = new Intent(this, Article.class);
            startActivity(intent);
        }
    }

    private void showFirstShowCase(){
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(articleBody))
                .hideOnTouchOutside()
                .setContentTitle("Scroll down to read entire article")
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
                .setTarget(new ViewTarget(goToHomePageButton))
                .hideOnTouchOutside()
                .setContentTitle("Click this button to go back to Home page")
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
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(mDrawerLayout))
                .hideOnTouchOutside()
                .setContentTitle("Slide from left to navigate between screens")
                .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                    }

                })
                .build();
    }
}

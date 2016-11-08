package com.cs442.group10.compost_crossing.newsArticle;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){

//        WriteArticleToDB writeArticleToDB = new WriteArticleToDB();
//        try{
//            writeArticleToDB.writeToDb();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();
        Log.i("Today's date :", dateFormat.format(date));
        String articleNumber = dateFormat.format(date);
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

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.news_article);

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
    }

    public void onClickingGoToHomePageButton(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

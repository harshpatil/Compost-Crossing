package com.cs442.group10.compost_crossing.newsArticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article);

        Intent intent = getIntent();

        articleTitle = (TextView) findViewById(R.id.articleTitle);
        articleBody = (TextView) findViewById(R.id.articleBody);
        image = (ImageView)findViewById(R.id.imageArticlePage);
        image.setImageResource(R.drawable.compost_1);

//        articleTitle.setText(intent.getStringExtra(Constants.ARTICLE_TITLE));
//        articleBody.setText(intent.getStringExtra(Constants.ARTICLE_BODY));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articlesList/news0");
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
//        writeToDB();

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

    public void writeToDB(){

        String title = "Title !!";
        String body = "Body !!!";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("articlesList");
        News news = new News();
        news.setTitle(title);
        news.setBody(body);
        myRef.child("news2").setValue(news);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.i("DataBase", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("DataBase", "Failed to read value.", error.toException());
            }
        });
    }
}

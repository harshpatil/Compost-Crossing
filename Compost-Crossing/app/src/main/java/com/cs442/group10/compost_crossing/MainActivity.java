package com.cs442.group10.compost_crossing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.newsArticle.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ViewListingFragment.OnListingSelectedListener {

    Button readArticle;
    String articleTitle;
    String articleBody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        fetchArticleFromDB();
    }

    public void onListingSelected(int position) {

        Intent compostDetailIntent = new Intent(this,CompostDetailActivity.class);
        startActivity(compostDetailIntent);
    }

    public void fetchArticleFromDB(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articlesList/news1");
        ref.push();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                News news = dataSnapshot.getValue(News.class);
                Log.i("FETCH_FIRBASE", " : Title : " + news.getTitle());
                Log.i("FETCH_FIRBASE", " : Body : " + news.getBody());
                articleTitle = news.getTitle();
                articleBody = news.getBody();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataBase", "Failed to read value.", databaseError.toException());
            }
        });

    }

    public void onClickingReadArticleButton(){

        Intent readArticleIntent = new Intent(this, Article.class);
//        readArticleIntent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
//        readArticleIntent.putExtra(Constants.ARTICLE_BODY, articleBody);
        startActivity(readArticleIntent);
    }
}
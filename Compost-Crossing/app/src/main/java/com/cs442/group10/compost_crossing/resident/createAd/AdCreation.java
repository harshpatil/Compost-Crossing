package com.cs442.group10.compost_crossing.resident.createAd;

import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.resident.historyPage.ResidentAdsHistory;
import com.cs442.group10.compost_crossing.resident.nearByComposter.NearByComposter;
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AdCreation extends AppCompatActivity {

    EditText compostTitle;
    EditText address;
    EditText city;
    EditText state;
    EditText zipCode;
    EditText weight;
    EditText price;
    Button submitButton;
    Button backButton;

    private ListView mDrawerList;
    private String[] drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_ad_creation_activity);

        compostTitle = (EditText) findViewById(R.id.compostType);
        address = (EditText) findViewById(R.id.pickupLocation);
        city = (EditText) findViewById(R.id.pickupCity);
        state = (EditText) findViewById(R.id.state);
        zipCode = (EditText) findViewById(R.id.zipCode);
        weight = (EditText) findViewById(R.id.compWeight);
        price = (EditText) findViewById(R.id.compPrice);
        submitButton = (Button) findViewById(R.id.btnSubmit);
        backButton = (Button) findViewById(R.id.backButtonCreateAdPage);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compostTitle.getText().toString().equals("") || address.getText().toString().equals("") || weight.getText().toString().equals("") || price.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    writeToDB();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingBackButton();
            }
        });

        // Naviagtion Code
        mDrawerList = (ListView) findViewById(R.id.left_drawer_module_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_module_list);

        drawerList = new String[5];
        drawerList[0] = Constants.HOME;
        drawerList[1] = Constants.NEWS_ARTICLE;
        drawerList[2] = Constants.YOUR_ACTIVE_ADS;
        drawerList[3] = Constants.YOUR_PAST_ADS;
        drawerList[4] = Constants.NEARBY_COMPOSTERS;

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_item, drawerList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_audiotrack, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                // getActionBar().setTitle("Ta-Helper");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                // getActionBar().setTitle("Ta-Helper Shortcuts");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    protected void writeToDB(){

        String adId = UUID.randomUUID().toString();

        CompostAd compostAd = new CompostAd();
        compostAd.setTitle(compostTitle.getText().toString());
        compostAd.setAddress(address.getText().toString());
        compostAd.setCity(city.getText().toString());
        compostAd.setState(state.getText().toString());
        compostAd.setZipCode(zipCode.getText().toString());
        compostAd.setWeight(weight.getText().toString());
        compostAd.setCost(price.getText().toString());
        compostAd.setSold("false");
        compostAd.setDrop("available");
        compostAd.setBuyerId("NULL");
        compostAd.setBuyerName("NULL");
        compostAd.setId(adId);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("adDetails");
        myRef.child(adId).setValue(compostAd);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("DataBase", "Failed to read value.", error.toException());
            }
        });

        Toast.makeText(this, "Compost Advertise named : \"" + compostAd.getTitle() + "\" has been created", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ResidentListViewActivity.class);
        startActivity(intent);
    }

    public void onClickingBackButton(){

        Intent intent = new Intent(this, ResidentListViewActivity.class);
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

        } else if(position == 2){

            Intent intent = new Intent(this, ResidentListViewActivity.class);
            startActivity(intent);

        } else if(position == 3){

            Intent intent = new Intent(this, ResidentAdsHistory.class);
            startActivity(intent);

        } else if(position == 4){

            Intent intent = new Intent(this, NearByComposter.class);
            startActivity(intent);

        }
    }
}

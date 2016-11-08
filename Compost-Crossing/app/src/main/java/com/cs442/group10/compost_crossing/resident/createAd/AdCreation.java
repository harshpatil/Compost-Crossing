package com.cs442.group10.compost_crossing.resident.createAd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.ResidentListViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    }

    protected void writeToDB(){

        String buyerId = "9595995";
        String idSuffix = "ad2";
        String adId = buyerId + "_" + idSuffix;

        CompostAd compostAd = new CompostAd();
        compostAd.setTitle(compostTitle.getText().toString());
        compostAd.setAddress(address.getText().toString());
        compostAd.setCity(city.getText().toString());
        compostAd.setState(state.getText().toString());
        compostAd.setZipCode(zipCode.getText().toString());
        compostAd.setWeight(weight.getText().toString());
        compostAd.setCost(price.getText().toString());
        compostAd.setDrop("available");
        compostAd.setBuyerId("NULL");
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

        Toast.makeText(this, "Compost Ad : " + compostAd.getTitle() + " has been created", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AdCreation.class);
        startActivity(intent);
    }

    public void onClickingBackButton(){

        Intent intent = new Intent(this, ResidentListViewActivity.class);
        startActivity(intent);
    }
}

package com.cs442.group10.compost_crossing.resident;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Josh on 10/31/2016.
 */
public class AdCreation extends AppCompatActivity{

    Button submitButton;
    EditText compostType;
    EditText pickAddress;
    EditText pickCity;
    EditText pickState;
    Switch paymentAccepted;
    EditText compWeight;
    EditText compPrice;

    String firstName = "John";//get from login
    String lastName  = "Sebastien";//get from login
    String type = "";
    String address = "";
    String state = "";
    String city = "";
    String dropAccept = "NO";
    String weight = "";
    String price = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_creation);

        compostType = (EditText) findViewById(R.id.compostType);
        pickAddress = (EditText) findViewById(R.id.pickupAddress);
        pickCity = (EditText) findViewById(R.id.pickupCity);
        pickState = (EditText) findViewById(R.id.pickupState);
        paymentAccepted = (Switch) findViewById(R.id.dropAccepted);
        compWeight = (EditText) findViewById(R.id.compWeight);
        compPrice = (EditText) findViewById(R.id.compPrice);

        submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compostType.getText().toString().equals("") || pickAddress.getText().toString().equals("") || pickCity.getText().toString().equals("") || pickState.getText().toString().equals("") || compWeight.getText().toString().equals("") || compPrice.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    writetoDB();
                }
            }
        });
    }

    protected void writetoDB(){
        type = compostType.getText().toString();
        if(paymentAccepted.isChecked()){
            dropAccept = "Available";
        }else {
            dropAccept = "Unavailable";
        }
        address = pickAddress.getText().toString();
        city = pickCity.getText().toString();
        state = pickState.getText().toString();
        weight = compWeight.getText().toString();
        price = compPrice.getText().toString();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("adDetails/ad3");
        mDatabase.child("adId").setValue(3);
        mDatabase.child("address").setValue(address);
        mDatabase.child("city").setValue(city);
        mDatabase.child("cost").setValue(price);
        mDatabase.child("drop").setValue(dropAccept);
        mDatabase.child("state").setValue(state);
        mDatabase.child("title").setValue(type);
        mDatabase.child("weight").setValue(weight);
        mDatabase.push();
    }
}
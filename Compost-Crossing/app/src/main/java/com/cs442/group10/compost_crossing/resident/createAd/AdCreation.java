package com.cs442.group10.compost_crossing.resident.createAd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdCreation extends AppCompatActivity {

    Button submitButton;
    EditText compostType;
    EditText pickupLocation;
    Switch paymentAccepted;
    EditText compWeight;
    EditText compPrice;

    String firstName = "John";//get from login
    String lastName  = "Sebastien";//get from login
    String type = "";
    String address = "";
    String state = "";
    String city = "";
    String payAccept = "Unavailable";
    double weight = 0.0;
    double price = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_ad_creation_activity);

        compostType = (EditText) findViewById(R.id.compostType);
        pickupLocation = (EditText) findViewById(R.id.pickupLocation);
        paymentAccepted = (Switch) findViewById(R.id.paymentAccepted);
        compWeight = (EditText) findViewById(R.id.compWeight);
        compPrice = (EditText) findViewById(R.id.compPrice);

        submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compostType.getText().toString().equals("") || pickupLocation.getText().toString().equals("") || compWeight.getText().toString().equals("") || compPrice.getText().toString().equals("")){
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
        String location = pickupLocation.getText().toString();
        if(paymentAccepted.isChecked()){
            payAccept = "Available";
        }else{
            payAccept = "Unavailable";
        }
        String[] result = location.split(", ");
        address = result[0];
        city = result[1];
        state = result[2];
        weight = Double.parseDouble(compWeight.getText().toString());
        price = Double.parseDouble(compPrice.getText().toString());

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("adDetails/ad3");
        mDatabase.child("Ad_id").setValue(3);
        mDatabase.child("Address").setValue(address);
        mDatabase.child("City").setValue(city);
        mDatabase.child("Cost").setValue(price);
        mDatabase.child("Drop").setValue(payAccept);
        mDatabase.child("State").setValue(state);
        mDatabase.child("Title").setValue(type);
        mDatabase.child("Weight").setValue(weight);
        mDatabase.push();
    }
}

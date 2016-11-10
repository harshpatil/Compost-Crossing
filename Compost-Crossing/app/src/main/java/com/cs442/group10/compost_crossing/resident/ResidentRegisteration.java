package com.cs442.group10.compost_crossing.resident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResidentRegisteration extends AppCompatActivity {
    public EditText name;
    public EditText phone;
    public EditText address;
    public EditText city;
    public EditText state;
    public EditText zipcode;
    DbMain db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_registeration);
        db = new DbMain(this);
        final Button createProfileButton= (Button)findViewById(R.id.createProfileButton);

        createProfileButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                name = (EditText)findViewById(R.id.firstName);
                phone = (EditText)findViewById(R.id.phoneNumber);
                address = (EditText)findViewById(R.id.address);
                city = (EditText)findViewById(R.id.city);
                state = (EditText)findViewById(R.id.state);
                zipcode = (EditText)findViewById(R.id.zipcode);
                if(name.getText().length() != 0 &&
                        phone.getText().length() != 0  &&
                        address.getText().length() != 0  &&
                        city.getText().length() != 0  &&
                        state.getText().length() != 0  &&
                        zipcode.getText().length() != 0 ) {
                    db.insertResident("", name.getText().toString(), phone.getText().toString(), address.getText().toString(), city.getText().toString(), state.getText().toString(), zipcode.getText().toString());
                    writetoDB(name.getText().toString(), phone.getText().toString(), address.getText().toString(), city.getText().toString(), state.getText().toString(), zipcode.getText().toString());

                    Intent residentListViewIntent = new Intent(getBaseContext(), ResidentListViewActivity.class);
                    startActivity(residentListViewIntent);

                }
                else{
                    Toast.makeText(getApplication(), "Please fill the form completely", Toast.LENGTH_LONG).show();
                }
                //sint count = db.numberOfentries();
            }
        });


    }

    protected void writetoDB( String name, String phone, String address, String city, String state, String zipcode){

        String url ="residentRegisteration/"+ phone;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(url);
        mDatabase.child("name").setValue(name);
        mDatabase.child("phone").setValue(phone);
        mDatabase.child("address").setValue(address);
        mDatabase.child("city").setValue(city);
        mDatabase.child("state").setValue(state);
        mDatabase.child("zipcode").setValue(zipcode);
        mDatabase.child("adlist").setValue(" ");
        mDatabase.push();
        Constants.ResidentId=phone.toString();
    }
}

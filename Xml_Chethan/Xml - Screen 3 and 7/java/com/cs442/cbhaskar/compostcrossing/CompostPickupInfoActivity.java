package com.cs442.cbhaskar.compostcrossing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class CompostPickupInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compost_pickup_info);

    }

    public void invokeSubmitButton(View v){
        Toast.makeText(this,"Compost ordered Successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}

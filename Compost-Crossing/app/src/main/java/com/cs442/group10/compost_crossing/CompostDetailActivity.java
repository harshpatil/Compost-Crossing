package com.cs442.group10.compost_crossing;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CompostDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compost_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){//For Landscape mode
            //fragmentTransaction.replace(R.id.compostDetailViewFragment, new CompostDetailViewFragment()); // list view fragment
            fragmentTransaction.replace(R.id.compostDetailViewFragment, new CompostDetailViewFragment());
        } else {//for Portrait mode
            fragmentTransaction.replace(R.id.compostDetailViewFragment, new CompostDetailViewFragment());
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

}

package com.cs442.group10.compost_crossing;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements ViewListingFragment.OnListingSelectedListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.compostButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.screen_2);
                ListView lv = (ListView) findViewById(R.id.expandableListView);
                // We need to use a different list item layout for devices older than Honeycomb
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, Listings.Names);
                lv.setAdapter(adapter);
                //lv.setOnClickListener(onListingSelected);
                /*
                //Fragment fragment = new RepeatEntry();
                //FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //category_cont is a linear layout container for my fragment
                ViewListingFragment viewFrag = new ViewListingFragment();
                ft.replace(R.id.fragment_container, viewFrag).addToBackStack("tag");
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();*/
            }
        });
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            ViewListingFragment firstFragment = new ViewListingFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            //Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }
    }

    public void onListingSelected(int position) {
        /*Code for fragment transaction when clicking on item*/
        Intent compostDetailIntent = new Intent(this,CompostDetailActivity.class);
        startActivity(compostDetailIntent);
    }
}
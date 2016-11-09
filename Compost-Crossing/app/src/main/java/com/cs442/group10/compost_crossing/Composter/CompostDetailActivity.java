package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;

public class CompostDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compost_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AdDetail adDetail = (AdDetail) bundle.getSerializable("compostAd");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {//For Landscape mode
                //fragmentTransaction.replace(R.id.compostDetailViewFragment, new CompostDetailViewFragment()); // list view fragment
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
            } else {//for Portrait mode
                fragmentTransaction.replace(R.id.compostDetailViewContainer, CompostDetailViewFragment.newInstance(adDetail));
                //fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
    }

    public void navigateBackToComposterAdsListView(View view){
        onBackPressed();
    }

    public void backToAdPage(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}

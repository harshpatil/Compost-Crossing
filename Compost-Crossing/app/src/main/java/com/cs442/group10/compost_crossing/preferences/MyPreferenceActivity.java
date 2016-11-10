package com.cs442.group10.compost_crossing.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.cs442.group10.compost_crossing.R;

public class MyPreferenceActivity extends PreferenceActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.userpreferences);
  }

}
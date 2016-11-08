package com.cs442.group10.compost_crossing.resident.nearByComposter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.cs442.group10.compost_crossing.resident.historyPage.Ads;

import java.util.List;

/**
 * Created by HarshPatil on 11/7/16.
 */
public class NearByComposterAdapter extends ArrayAdapter<Composters> {

    Context context;
    int resource;

    public NearByComposterAdapter(Context context, int resource, List<Composters> compostersList){

        super(context, resource, compostersList);
        this.context = context;
        this.resource = resource;
    }

}

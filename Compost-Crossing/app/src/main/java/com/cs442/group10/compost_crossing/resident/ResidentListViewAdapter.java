package com.cs442.group10.compost_crossing.resident;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.R;

/**
 * Created by cheth on 10/31/2016.
 */

public class ResidentListViewAdapter extends BaseAdapter{

    Activity activity;
    String[] ads = {
            "Ad 1",
            "Ad 2",
            "Ad 3",
            "Ad 422"
    };

    public ResidentListViewAdapter(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return ads.length;
    }

    @Override
    public String getItem(int position) {
        return ads[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.resident_item_list, null);
        }
        TextView adTextView = (TextView) convertView.findViewById(R.id.compostAdTextView);
        adTextView.setText(getItem(position).toString());
        return convertView;
    }
}

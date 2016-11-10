package com.cs442.group10.compost_crossing.Composter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheth on 11/8/2016.
 */

public class ComposterListViewAdapter extends BaseAdapter{
    Activity activity;
    //ArrayList<String> compostAdsList;
    Map<String,String> compostAdsMap = new HashMap<String,String>();

    public ComposterListViewAdapter(Activity activity, Map<String,String> compostAdsMap) {
        super();
        this.activity = activity;
        this.compostAdsMap = compostAdsMap;
    }

    @Override
    public int getCount() {
        return compostAdsMap.size();
    }

    @Override
    public String getItem(int position) {
        return compostAdsMap.get(compostAdsMap.keySet().toArray()[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.composter_item_list, null);
        }
        TextView adTextView = (TextView) convertView.findViewById(R.id.compostAdTextView);
        String compostAdName = (String) compostAdsMap.keySet().toArray()[position];
        String currentItem = getItem(position).toString();
        adTextView.setText(currentItem);

        return convertView;
    }
}

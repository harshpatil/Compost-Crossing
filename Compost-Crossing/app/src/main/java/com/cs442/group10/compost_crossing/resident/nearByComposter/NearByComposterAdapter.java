package com.cs442.group10.compost_crossing.resident.nearByComposter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.resident.historyPage.Ads;

import java.util.List;

/**
 * Created by HarshPatil on 11/7/16.
 */
public class NearByComposterAdapter extends ArrayAdapter<Composter> {

    Context context;
    int resource;

    public NearByComposterAdapter(Context context, int resource, List<Composter> composterList){

        super(context, resource, composterList);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout composterListView;
        final Composter composter = getItem(position);

        if(convertView == null){
            composterListView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, composterListView, true);
        }
        else {
            composterListView = (RelativeLayout) convertView;
        }

        TextView composterName = (TextView) composterListView.findViewById(R.id.ComposterNameResidentNearbyComposterPage);
        TextView composterAddress = (TextView) composterListView.findViewById(R.id.ComposterAddressResidentNearByComposterPage);
        ImageView imageView = (ImageView) composterListView.findViewById(R.id.imageResidentNearByComposterPage);

        composterName.setText("Composter Name : "+ composter.getName());
        composterAddress.setText("Address : "+ String.valueOf(composter.getAddress()));
        imageView.setImageResource(composter.getImageId());

        return composterListView;
    }
}

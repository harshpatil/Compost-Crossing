package com.cs442.group10.compost_crossing.Composter.nearbyResident;

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
public class NearByResidentAdapter extends ArrayAdapter<Resident> {

    Context context;
    int resource;

    public NearByResidentAdapter(Context context, int resource, List<Resident> composterList){

        super(context, resource, composterList);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout composterListView;
        final Resident composter = getItem(position);

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
        TextView composterCost = (TextView) composterListView.findViewById(R.id.ComposterCostResidentNearByComposterPage);
        ImageView imageView = (ImageView) composterListView.findViewById(R.id.imageResidentNearByComposterPage);

        composterName.setText("Title : "+ composter.getTitle());
        composterAddress.setText("Weight : "+ String.valueOf(composter.getWeight()));
        composterCost.setText("Cost : "+ String.valueOf(composter.getCost()));
        imageView.setImageResource(composter.getImageId());

        return composterListView;
    }
}

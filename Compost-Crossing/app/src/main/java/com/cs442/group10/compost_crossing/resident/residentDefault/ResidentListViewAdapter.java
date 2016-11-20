package com.cs442.group10.compost_crossing.resident.residentDefault;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheth on 10/31/2016.
 */

public class ResidentListViewAdapter extends ArrayAdapter<AdDetail>{

    Activity activity;
    List<AdDetail> adDetailList = new ArrayList<>();

    public ResidentListViewAdapter(Context context, int resource, Activity activity, List<AdDetail> adDetailList) {
        super(context,resource,adDetailList);
        this.activity = activity;
        this.adDetailList = adDetailList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.resident_item_list, null);
        }

        AdDetail adDetail = getItem(position);

        TextView adTitle = (TextView) convertView.findViewById(R.id.compostTitleTextViewResidentPage);
        TextView adWeight = (TextView) convertView.findViewById(R.id.compostWeightTextViewResidentPage);
        TextView adCost = (TextView) convertView.findViewById(R.id.compostCostTextViewResidentPage);
        ImageView adImage = (ImageView) convertView.findViewById(R.id.compostImageResidentItemPage);

        adTitle.setText("Title : "+ adDetail.getTitle());
        adCost.setText("Cost : $"+ adDetail.getCost());
        adWeight.setText("Weight : "+ adDetail.getWeight()+" lbs");
        adImage.setImageResource(adDetail.getImageId());

        return convertView;
    }
}

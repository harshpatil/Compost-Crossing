package com.cs442.group10.compost_crossing.Composter;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheth on 11/8/2016.
 */

public class ComposterListViewAdapter extends ArrayAdapter<AdDetail>{
    Activity activity;
    List<AdDetail> compostAdDetailList;
    Map<String,String> compostAdsMap = new HashMap<String,String>();

    public ComposterListViewAdapter(Context context, int resource, Activity activity, List<AdDetail> compostAdDetailList) {
        super(context,resource,compostAdDetailList);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.composter_item_list, null);
        }

        AdDetail adDetail = getItem(position);

        TextView adTitle = (TextView) convertView.findViewById(R.id.compostTitleTextViewCompostPage);
        TextView adWeight = (TextView) convertView.findViewById(R.id.compostWeightTextViewCompostPage);
        TextView adCost = (TextView) convertView.findViewById(R.id.compostCostTextViewCompostPage);
        ImageView adImage = (ImageView) convertView.findViewById(R.id.compostImageCompostItemPage);

        //convertView.setId(Integer.parseInt(adDetail.getId()));
        adTitle.setText("Title : "+ adDetail.getTitle());
        adCost.setText("Cost : "+ adDetail.getCost());
        adWeight.setText("Weight : "+ adDetail.getWeight() +" lbs");
        adImage.setImageResource(adDetail.getImageId());

        return convertView;
    }
}

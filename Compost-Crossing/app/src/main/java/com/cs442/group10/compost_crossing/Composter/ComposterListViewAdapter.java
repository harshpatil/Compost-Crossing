package com.cs442.group10.compost_crossing.Composter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chethan on 11/8/2016.
 */

public class ComposterListViewAdapter extends BaseAdapter{
    Activity activity;
    final List<AdDetail> compostAdDetailList;
    Map<String,String> compostAdsMap = new HashMap<String,String>();

    public ComposterListViewAdapter(Context context, int resource, Activity activity, List<AdDetail> compostAdDetailList) {
        super();
        this.compostAdDetailList = compostAdDetailList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return compostAdDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return compostAdDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        AdDetail adDetail = (AdDetail) getItem(position);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.composter_item_list, null);
        }

        TextView adTitle = (TextView) convertView.findViewById(R.id.compostTitleTextViewCompostPage);
        TextView adWeight = (TextView) convertView.findViewById(R.id.compostWeightTextViewCompostPage);
        TextView adCost = (TextView) convertView.findViewById(R.id.compostCostTextViewCompostPage);
        ImageView adImage = (ImageView) convertView.findViewById(R.id.compostImageCompostItemPage);

        adTitle.setText("Title : " + adDetail.getTitle());
        adCost.setText("Cost : $" + adDetail.getCost());
        adWeight.setText("Weight : " + adDetail.getWeight() + " lbs");
        adImage.setImageResource(adDetail.getImageId());
        return convertView;
    }
}

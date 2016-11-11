package com.cs442.group10.compost_crossing.resident.historyPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.R;

import java.util.List;

/**
 * Created by HarshPatil on 11/7/16.
 */
public class ResidentHistoryAdapter extends ArrayAdapter<Ads> {

    Context context;
    int resource;

    public ResidentHistoryAdapter(Context context, int resource, List<Ads> adsList){

        super(context, resource, adsList);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout adsView;
        final Ads ads = getItem(position);

        if(convertView == null){
            adsView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, adsView, true);
        }
        else {
            adsView = (RelativeLayout) convertView;
        }

        TextView adTitle = (TextView)adsView.findViewById(R.id.AdTitleResidentHistoryPage);
        TextView boughtBy = (TextView)adsView.findViewById(R.id.CompostBoughtByHistoryPage);
        TextView compostWeight = (TextView)adsView.findViewById(R.id.CompostWeightResidentHistoryPage);
        TextView compostPrice = (TextView)adsView.findViewById(R.id.AdCostResidentHistoryPage);
        ImageView imageView = (ImageView) adsView.findViewById(R.id.imageResidentHistoryPage);

        adTitle.setText("Title : "+ ads.getTitle());
        boughtBy.setText("Bought By : "+ String.valueOf(ads.getBuyerName()));
        compostWeight.setText("Weight : "+ String.valueOf(ads.getWeight()) + " pounds");
        compostPrice.setText("Cost : $"+ String.valueOf(ads.getCost()));
        imageView.setImageResource(ads.getImageId());

        return adsView;
    }

}

package com.cs442.group10.compost_crossing.Composter.historyPage;

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
public class ComposterHistoryAdaptor extends ArrayAdapter<Ads> {

    Context context;
    int resource;

    public ComposterHistoryAdaptor(Context context, int resource, List<Ads> adsList){

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

        TextView adTitle = (TextView)adsView.findViewById(R.id.AdTitleComposterHistoryPage);
        TextView compostWeight = (TextView)adsView.findViewById(R.id.CompostWeightComposterHistoryPage);
        TextView compostPrice = (TextView)adsView.findViewById(R.id.AdCostComposterHistoryPage);
        ImageView imageView = (ImageView) adsView.findViewById(R.id.imageComposterHistoryPage);

        adTitle.setText("Title : "+ ads.getTitle());
        compostWeight.setText("Weight : "+ String.valueOf(ads.getWeight()) + " pounds");
        compostPrice.setText("Cost : $"+ String.valueOf(ads.getCost()));
        imageView.setImageResource(ads.getImageId());

        return adsView;
    }

}

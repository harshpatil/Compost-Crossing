package com.cs442.group10.compost_crossing.resident;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheth on 10/31/2016.
 */

public class ResidentListViewAdapter extends BaseAdapter{

    Activity activity;
    //ArrayList<String> compostAdsList;
    Map<String,String> compostAdsMap = new HashMap<String,String>();

    public ResidentListViewAdapter(Activity activity, Map<String,String> compostAdsMap) {
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
            convertView = layoutInflater.inflate(R.layout.resident_item_list, null);
        }
        TextView adTextView = (TextView) convertView.findViewById(R.id.compostAdTextView);
        final String compostAdName = (String) compostAdsMap.keySet().toArray()[position];
        final String currentItem = getItem(position).toString();
        adTextView.setText(currentItem);

        Button removeAdBtn = (Button) convertView.findViewById(R.id.btnAdRemove);
        removeAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        // .setTitle("Remove Compost Ad")
                        .setMessage("Remove "+ currentItem +" Ad?")
                        // .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("adDetails").child(compostAdName);

                                reference.removeValue();
                                Toast.makeText(activity.getBaseContext(), currentItem+" removed", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return convertView;
    }
}

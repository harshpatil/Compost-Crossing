package com.cs442.group10.compost_crossing.Composter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheth on 10/29/2016.
 */

public class CompostDetailViewFragment extends Fragment {
    TextView composterAddressTextView;
    String composterAddress;
    String composterAddressForMap;
    static AdDetail adDetail;

    public static CompostDetailViewFragment newInstance(AdDetail compostAdDetail){
        CompostDetailViewFragment compostDetailViewFragment =  new CompostDetailViewFragment();

        adDetail = compostAdDetail;
        return compostDetailViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compost_detail_view_fragment, container, false);

        TextView composterPhNoTextView = (TextView) view.findViewById(R.id.composterPhNo);
        composterAddressTextView = (TextView) view.findViewById(R.id.composterAddr);
        TextView compostDetailsTextView = (TextView) view.findViewById(R.id.compostDetails);
        Button backButton = (Button) view.findViewById(R.id.btnBackComposterDetailView);
        Button acceptCompostButton = (Button) view.findViewById(R.id.btnAcceptCompost);

        composterAddressForMap = adDetail.getAddress()+","+adDetail.getCity()+","+adDetail.getState()+"-"+adDetail.getZipCode();//"3001 S King Drive,Illinois,Chicago-60616";
        composterAddress = adDetail.getAddress()+",\n"+adDetail.getCity()+", "+adDetail.getState()+" - "+adDetail.getZipCode();
        composterAddressTextView.setText("3001 S King Drive,\n Illinois, Chicago - 60616");

        new GetMapsInfo().execute(composterAddressForMap.replaceAll(" ","%20"));

        composterPhNoTextView.setText("3152547895");
        Linkify.addLinks(composterPhNoTextView, Linkify.PHONE_NUMBERS);

        compostDetailsTextView.setText(adDetail.getTitle()+"\nWeight: "+adDetail.getWeight()+"\nCost:$"+adDetail.getCost()+"\nDrop:"+adDetail.getDrop());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ComposterListViewActivity.class);
                startActivity(intent);
                //getActivity().onBackPressed();
            }
        });

        acceptCompostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("adDetails");
                reference.child("ad1").child("buyerId").setValue("John");

                DatabaseReference reference1 = database.getReference("composterRegisteration");
                Map<String, Map<String, String>> compostAdMap = getCompostAdMap();

                reference1.child("9174032678").child("adList").setValue(compostAdMap);

                Toast.makeText(getActivity().getBaseContext(), "Compost Accepted Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), ComposterListViewActivity.class);
                //intent.putExtra("loadCompostDetailFragment", false);
                startActivity(intent);
               // getActivity().onBackPressed();
            }
        });
        return view;
    }

    private HashMap<String, Map<String,String>> getCompostAdMap(){
        HashMap<String, Map<String,String>> compostAdListMap = new HashMap<String, Map<String,String>>();
        Map<String,String> compostAdMap = new HashMap<String,String>();

        compostAdMap.put("id", adDetail.getId());
        compostAdMap.put("address", adDetail.getAddress());
        compostAdMap.put("buyerId", adDetail.getBuyerId());
        compostAdMap.put("buyerName", "John");
        compostAdMap.put("city", adDetail.getCity());
        compostAdMap.put("cost", adDetail.getCost());
        compostAdMap.put("drop", adDetail.getDrop());
        compostAdMap.put("state", adDetail.getState());
        compostAdMap.put("title", adDetail.getTitle());
        compostAdMap.put("weight", adDetail.getWeight());
        compostAdMap.put("sold", "true");
        compostAdMap.put("zipCode", adDetail.getZipCode());

        compostAdListMap.put(compostAdMap.get("id"), compostAdMap);
        return compostAdListMap;
    }

    class GetMapsInfo extends AsyncTask<String, String, String>{
        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if(android.os.Debug.isDebuggerConnected())//TODO: Remove
                android.os.Debug.waitForDebugger();
            String response;
            String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                    params[0] + "&sensor=false";
            response = getLatLng(params[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if(android.os.Debug.isDebuggerConnected())//TODO: Remove
                android.os.Debug.waitForDebugger();
            dialog.dismiss();
            super.onPostExecute(s);
            JSONObject jsonObject;
            double lat = 0.0;
            double lng = 0.0;

            try {
                jsonObject = new JSONObject(s);
                System.out.println("result[0]: "+s);
                lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                System.out.println("Lat: "+lat);
                composterAddressTextView.setText(
                        Html.fromHtml("<a href = \"http://maps.google.com/maps?q="+lat+","+lng+"\">"+composterAddress+"</a>"));
                composterAddressTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getLatLng(String address){
        StringBuilder responseStringBuilder = new StringBuilder();
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                address + "&sensor=false";

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseStringBuilder.append(line);
                }
            } else {
                responseStringBuilder.append("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStringBuilder.toString();
    }
}

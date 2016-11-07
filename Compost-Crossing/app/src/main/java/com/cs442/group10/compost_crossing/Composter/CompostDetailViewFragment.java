package com.cs442.group10.compost_crossing.Composter;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs442.group10.compost_crossing.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by cheth on 10/29/2016.
 */

public class CompostDetailViewFragment extends Fragment {
    TextView composterAddressTextView;
    String composterAddress;

    public CompostDetailViewFragment(){
    }

    public static CompostDetailViewFragment newInstance(int i){
        CompostDetailViewFragment compostDetailViewFragment =  new CompostDetailViewFragment();
        return compostDetailViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compost_detail_view_fragment, container, false);
        //LatLng latLng = new LatLng();

        TextView composterPhNoTextView = (TextView) view.findViewById(R.id.composterPhNo);
        composterAddressTextView = (TextView) view.findViewById(R.id.composterAddr);
        TextView compostDetailsTextView = (TextView) view.findViewById(R.id.compostDetails);

        composterAddress = "3001 S King Drive,Illinois,Chicago-60616";

        composterAddressTextView.setText("3001 S King Drive,\n Illinois, Chicago - 60616");

        new GetMapsInfo().execute(composterAddress.replaceAll(" ","%20"));

        composterPhNoTextView.setText("3152547895");
        Linkify.addLinks(composterPhNoTextView, Linkify.PHONE_NUMBERS);

        compostDetailsTextView.setText("Weight: 20 pounds\nCost:$1/pound\nDrop:Available");

        return view;
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
            if(android.os.Debug.isDebuggerConnected())//TODO: removed
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

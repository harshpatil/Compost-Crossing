package com.cs442.group10.compost_crossing.Composter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.AdDetail;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link CompostDetailViewFragment} - Fragment to display particular Ad details.
 * Created by Chethan on 10/29/2016.
 */

public class CompostDetailViewFragment extends Fragment {

    private static final String COMPOSTER_REG_TABLE = "composterRegisteration";
    private static final String RESIDENT_REG_TABLE = "residentRegisteration";
    final String composterId = Constants.composterId;
    final String composterName = Constants.composterName;
    TextView composterAddressTextView;
    String composterAddress;
    String composterAddressForMap;
    public static AdDetail adDetail;
    View mainView;

    /**
     * Method to return a new instance of {@link CompostDetailViewFragment}
     *
     * @param compostAdDetail
     * @return Fragment
     */
    public static CompostDetailViewFragment newInstance(AdDetail compostAdDetail) {
        CompostDetailViewFragment compostDetailViewFragment = new CompostDetailViewFragment();
        adDetail = compostAdDetail;
        return compostDetailViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.compost_detail_view_fragment, container, false);
        Button backButton = (Button) mainView.findViewById(R.id.btnBackComposterDetailView);
        Button acceptCompostButton = (Button) mainView.findViewById(R.id.btnAcceptCompost);

        if(adDetail != null) {
            setCompostDetailViewValues();
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ComposterListViewActivity.class);
                startActivity(intent);
            }
        });

        acceptCompostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adDetail != null) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();

                    updateResidentTable(database, composterId, composterName);
                    updateComposterTable(database, composterId, composterName);

                    int res = getContext().checkCallingOrSelfPermission("android.permission.SEND_SMS");
                    if (res == PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(adDetail.getOwnerPhone(), null, "Yay!\n" + adDetail.getTitle() + " accepted by " + composterName, null, null);
                        Log.i("Compost Crossing", "Sms sent to " + adDetail.getOwnerPhone() + " Successfully!");
                    }

                    Toast.makeText(getActivity().getBaseContext(), R.string.CompostAcceptedSuccessMsg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), ComposterListViewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), R.string.selectCompostMessage,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mainView;
    }

    public void setCompostDetailViewValues() {
        TextView composterNameTextView = (TextView) mainView.findViewById(R.id.composterName);
        TextView composterPhNoTextView = (TextView) mainView.findViewById(R.id.composterPhNo);
        TextView compostDetailsTextView = (TextView) mainView.findViewById(R.id.compostDetails);

        composterNameTextView.setText(adDetail.getOwnerName());
        composterAddressTextView = (TextView) mainView.findViewById(R.id.composterAddr);
        composterAddressForMap = adDetail.getAddress() + "," + adDetail.getCity() + "," + adDetail.getState() + "-" + adDetail.getZipCode();//"3001 S King Drive,Illinois,Chicago-60616";
        composterAddress = adDetail.getAddress() + ",\n" + adDetail.getCity() + ", " + adDetail.getState() + " - " + adDetail.getZipCode();

        new GetMapsInfo().execute(composterAddressForMap.replaceAll(" ", "%20"));

        composterPhNoTextView.setText(adDetail.getOwnerPhone());
        Linkify.addLinks(composterPhNoTextView, Linkify.PHONE_NUMBERS);

        compostDetailsTextView.setText(adDetail.getTitle() + "\nWeight: " + adDetail.getWeight() + " lbs\nCost: $" + adDetail.getCost());
    }

    /**
     * Method to update database for resident table marking the sold Ads.
     *
     * @param database
     * @param composterId
     * @param composterName
     */
    private void updateResidentTable(FirebaseDatabase database, String composterId, String composterName) {
        DatabaseReference residentAdIdRef = database.getReference(RESIDENT_REG_TABLE).child(adDetail.getOwnerPhone()).child("adlist").child(adDetail.getId());
        residentAdIdRef.child("sold").setValue("true");
        residentAdIdRef.child("buyerId").setValue(composterId);
        residentAdIdRef.child("buyerName").setValue(composterName);
    }

    /**
     * Method to update database for composter table in order to display purchase history.
     *
     * @param database
     * @param composterId
     * @param composterName
     */
    private void updateComposterTable(FirebaseDatabase database, String composterId, String composterName) {
        Map<String, String> compostAdMap = getCompostAdMap(composterId, composterName);
        DatabaseReference composterTableRef = database.getReference(COMPOSTER_REG_TABLE);
        composterTableRef.child(composterId).child("adList").child(adDetail.getId()).setValue(compostAdMap);
    }

    /**
     * Method to prepare Compost Ad Details data for a particular Ad.
     *
     * @param composterId
     * @param composterName
     * @return Map<String,String>
     */
    private Map<String, String> getCompostAdMap(String composterId, String composterName) {
        Map<String, String> compostAdMap = new HashMap<String, String>();

        compostAdMap.put("id", adDetail.getId());
        compostAdMap.put("address", adDetail.getAddress());
        compostAdMap.put("buyerId", composterId);
        compostAdMap.put("buyerName", composterName);
        compostAdMap.put("city", adDetail.getCity());
        compostAdMap.put("cost", adDetail.getCost());
        compostAdMap.put("state", adDetail.getState());
        compostAdMap.put("title", adDetail.getTitle());
        compostAdMap.put("weight", adDetail.getWeight());
        compostAdMap.put("sold", "true");
        compostAdMap.put("zipCode", adDetail.getZipCode());

        return compostAdMap;
    }

    /**
     * Asynchronous task for navigation to Maps and adding Linkify features to Contact.
     */
    class GetMapsInfo extends AsyncTask<String, String, String> {
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
            String response;
            String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                    params[0] + "&sensor=false";
            response = getLatLng(params[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            super.onPostExecute(s);
            JSONObject jsonObject;
            double lat = 0.0;
            double lng = 0.0;

            try {
                jsonObject = new JSONObject(s);
                System.out.println("result[0]: " + s);
                lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                System.out.println("Lat: " + lat);
                composterAddressTextView.setText(
                        Html.fromHtml("<a href = \"http://maps.google.com/maps?q=" + lat + "," + lng + "\">" + composterAddress + "</a>"));
                composterAddressTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("Compost Crossing", "Error in Compost Detail View Fragment-->onPostExecute method");
            }
        }
    }

    /**
     * Method to get latitude and longitude of a particular address.
     *
     * @param address
     * @return String - Latitude and Longitude
     */
    private String getLatLng(String address) {
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

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }
            } else {
                responseStringBuilder.append("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Compost Crossing", "Error in Compost Detail View Fragment-->getLatLng method");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Compost Crossing", "Error in Compost Detail View Fragment-->getLatLng method");
        }
        return responseStringBuilder.toString();
    }


}

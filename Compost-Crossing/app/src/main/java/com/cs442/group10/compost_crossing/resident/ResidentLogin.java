package com.cs442.group10.compost_crossing.resident;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;
import com.cs442.group10.compost_crossing.resident.residentDefault.ResidentListViewActivity;

import java.util.Map;

public class ResidentLogin extends AppCompatActivity {

    public EditText username;
    public EditText passcode;
    DbMain db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbMain(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_login);

        TextView forgotPasswordTextView = (TextView) findViewById(R.id.forgotPasswordLink);
        if (forgotPasswordTextView != null) {
            SpannableString spannableString = new SpannableString(getResources().getString(R.string.forgot_user_credentials));

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    int res = getApplicationContext().checkCallingOrSelfPermission("android.permission.SEND_SMS");
                    if (res == PackageManager.PERMISSION_GRANTED) {
                        String residentName = Constants.residentName;
                        Map<String, String> userCredentialsMap = db.getResidentDetails();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(Constants.residentId, null, "Hi " + residentName + ",\n Your Resident login credentials:\nUsername:" + userCredentialsMap.get("userName") + "\nPassword: " + userCredentialsMap.get("password"), null, null);
                        Toast.makeText(getApplicationContext(), "Resident User credentials will reach you shortly via registered contact number.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.EnableSmsPermMsg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            forgotPasswordTextView.setText(spannableString);
            forgotPasswordTextView.setLinkTextColor(getResources().getColor(R.color.cast_libraries_material_featurehighlight_outer_highlight_default_color));
            forgotPasswordTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        username = (EditText) findViewById(R.id.username);
        passcode = (EditText) findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (username.getText().length() != 0 &&
                        passcode.getText().length() != 0) {
                    String dbpasscode = db.getResidentUserNameAndPassword(username.getText().toString());
                    String userid = db.getResidentUserName();
                    if (dbpasscode.equals(passcode.getText().toString()) && userid.equals(username.getText().toString())) {
                        Constants.loginflagforresident = 1;
                        db.setComposterUserDetails();//Sets the composter name,id and zip - Chethan
                        Intent composterListViewIntent = new Intent(getBaseContext(), ResidentListViewActivity.class);
                        startActivity(composterListViewIntent);
                        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                    } else if(username.getText().length() < 3){
                        Toast.makeText(getApplication(), R.string.minLengthUserNameMsg, Toast.LENGTH_LONG).show();
                    } else if(passcode.getText().length() < 3){
                        Toast.makeText(getApplication(), R.string.minLengthPasswordMsg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), R.string.incorrectUserCredentialsMsg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplication(), R.string.emptyLoginDetailsMsg, Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}

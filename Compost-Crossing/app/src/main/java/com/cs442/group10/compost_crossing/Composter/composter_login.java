package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
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

import java.util.Map;

public class composter_login extends AppCompatActivity {

    public EditText username;
    public EditText passcode;
    DbMain db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbMain(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_login);

        TextView forgotPasswordLink = (TextView) findViewById(R.id.forgotPasswordLink);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.forgot_user_credentials));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String composterName = Constants.composterName;
                Map<String, String> userCredentialsMap = db.getComposterDetails();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(Constants.composterId, null, "Hi " + composterName + ",\n Your Composter login credentials:\nUsername:" + userCredentialsMap.get("userName") + "\nPassword: " + userCredentialsMap.get("password"), null, null);
                Toast.makeText(getApplicationContext(), "Composter User credentials will reach you shortly via registered contact number", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgotPasswordLink.setText(spannableString);
        forgotPasswordLink.setLinkTextColor(getResources().getColor(R.color.cast_libraries_material_featurehighlight_outer_highlight_default_color));
        forgotPasswordLink.setMovementMethod(LinkMovementMethod.getInstance());

        username = (EditText) findViewById(R.id.username);
        passcode = (EditText) findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (username.getText().length() != 0 &&
                        passcode.getText().length() != 0) {
                    String dbpasscode = db.getComposterUserNameAndPassword(username.getText().toString());
                    String userid = db.getComposterUserName();
                    if (dbpasscode.equals(passcode.getText().toString()) && userid.equals(username.getText().toString())) {
                        Constants.loginflag = 1;
                        db.setComposterUserDetails();//Sets the composter name,id and zip - Chethan
                        Intent composterListViewIntent = new Intent(getBaseContext(), ComposterListViewActivity.class);
                        startActivity(composterListViewIntent);
                        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
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

package com.cs442.group10.compost_crossing.Composter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs442.group10.compost_crossing.DB.DbMain;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;

public class composter_login extends AppCompatActivity {

    public EditText username;
    public EditText passcode;
    DbMain db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbMain(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composter_login);

        username = (EditText)findViewById(R.id.username);
        passcode = (EditText)findViewById(R.id.password);
        final Button loginButton= (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(username.getText().length() != 0 &&
                        passcode.getText().length() != 0 ){
                    String dbpasscode = db.getComposterUserNameAndPassword(username.getText().toString());
                    String userid = db.getComposterUserName();
                    if(dbpasscode.equals(passcode.getText().toString()) && userid.equals(username.getText().toString()) ){
                        Constants.loginflag=1;
                        db.setComposterUserDetails();//Sets the composter name,id and zip - Chethan
                        Intent composterListViewIntent = new Intent(getBaseContext(), ComposterListViewActivity.class);
                        startActivity(composterListViewIntent);
                        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                    }
                    else{
                        Toast.makeText(getApplication(), "Incorrect Password or User Name", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }
}

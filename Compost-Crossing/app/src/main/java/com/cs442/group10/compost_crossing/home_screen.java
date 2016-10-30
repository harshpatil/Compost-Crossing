package ssunda15.compostcrossing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final Button Composter= (Button)findViewById(R.id.compostButton);
        final Button residentButton= (Button)findViewById(R.id.residentButton);

        Composter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), "give the required class name");
                startActivity(i);

            }
        });

        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), "give the required class name");
                startActivity(i);

            }
        });
    }
}

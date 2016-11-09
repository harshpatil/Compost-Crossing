package ssunda15.linkify;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t1 =(TextView)findViewById (R.id.textView);
       // t1.setMovementMethod(LinkMovementMethod.getInstance());


        TextView t2 =(TextView)findViewById (R.id.textView2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t3 =(TextView)findViewById (R.id.textView3);
        t3.setText(
                Html.fromHtml(
                  "<b>text 3: Constructed from HTML programmatically.</b> text with a"+
                          "<a href=\"http://www.google.com\">link</a>"+
                          "created in the java source code using html."));

        t3.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ss = new SpannableString(
                "text 4:Manually created spans. click here to dial the phone");
        ss.setSpan(new StyleSpan(Typeface.BOLD),0,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new URLSpan("tel:4155551212"),31+6,31+10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView t4 = (TextView) findViewById(R.id.textView4);
        t4.setText(ss);
        t4.setMovementMethod(LinkMovementMethod.getInstance());

    }
}

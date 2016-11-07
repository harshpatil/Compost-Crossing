package com.cs442.group10.compost_crossing.newsArticle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by HarshPatil on 11/1/16.
 */
public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Alarm Received!", "Inside onReceive method of Alarm class");
        Intent i = new Intent(context, ArticleNotificationService.class);
        context.startService(i);
    }
}

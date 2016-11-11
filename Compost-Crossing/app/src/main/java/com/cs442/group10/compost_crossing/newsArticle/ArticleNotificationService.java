package com.cs442.group10.compost_crossing.newsArticle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;
import com.cs442.group10.compost_crossing.constants.Constants;

/**
 * Created by HarshPatil on 10/31/16.
 */
public class ArticleNotificationService extends Service {

    @Override
    public void onCreate() {
        Log.i("ServiceClass", "Inside onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isNotification = sharedPref.getBoolean("PREF_CHECK_BOX",false);

        Log.i("IS_NOTIFICATION : ", String.valueOf(isNotification));

        if(isNotification){

            Log.i("ServiceClass", "Inside onStartCommand ");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Article.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Compost Article")
                    .setContentText("New Article has been published. Have a look.")
                    .setOngoing(false)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[]{100, 100, 100, 100, 100});
            notificationCompat.setContentIntent(contentIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Constants.ARTICLE_NOTIFICATION_ID, notificationCompat.build());
        }

        onDestroy();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("ServiceClass", "Inside onDestory");
    }

}

package com.hit.wegoal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by 26049_000 on 2017/12/9.
 */

public class alarmreceiver extends BroadcastReceiver {
    private String goalname;
    @Override
    public void onReceive(Context context, Intent intent) {
        goalname=intent.getStringExtra("goalname");
        Intent intentnew=new Intent(context,backappreceiver.class);
        PendingIntent p=PendingIntent.getBroadcast(context,0,intentnew,0);
        NotificationManager manager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentTitle("目标："+goalname)
                .setContentText("不要停下追逐梦想的脚步")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .setContentIntent(p)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }
}

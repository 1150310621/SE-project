package com.hit.wegoal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 26049_000 on 2017/12/9.
 */

public class backappreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(SystemUtils.isAppAlive(context, "com.hit.wegoal")){
            Intent mainIntent = new Intent(context, goalpage.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        }else {
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.hit.wegoal");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(launchIntent);
        }
    }
}

package com.vrlc92.liskmonitor.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vrlc92.liskmonitor.utils.Utils;

/**
 * Created by victorlins on 5/21/16.
 */
public class ForgingBootReceiver extends BroadcastReceiver {
    private final ForgingAlarmReceiver alarm = new ForgingAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) && Utils.alarmEnabled(context)) {
            alarm.setAlarm(context);
        }
    }
}
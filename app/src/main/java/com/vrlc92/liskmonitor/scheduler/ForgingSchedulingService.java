package com.vrlc92.liskmonitor.scheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.vrlc92.liskmonitor.MainActivity;
import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Block;
import com.vrlc92.liskmonitor.models.Settings;
import com.vrlc92.liskmonitor.services.LiskService;
import com.vrlc92.liskmonitor.services.RequestListener;
import com.vrlc92.liskmonitor.utils.Utils;

/**
 * Created by victorlins on 5/19/16.
 */
public class ForgingSchedulingService extends IntentService {
    private static String TAG = ForgingSchedulingService.class.getSimpleName();

    public ForgingSchedulingService() {
        super(TAG);
    }

    public static final int NOTIFICATION_ID = 1;
    private static final long THIRTY_MINUTES_IN_MILLISECONDS = 1800000;

    private NotificationManager mNotificationManager;
    private Intent mIntent;

    @Override
    protected void onHandleIntent(Intent intent) {
        this.mIntent = intent;
        loadLastForgedBlock();
    }

    private void loadLastForgedBlock() {
        Settings settings = Utils.getSettings(getApplicationContext());

        LiskService.getInstance().requestLastBlockForged(settings, new RequestListener<Block>() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, e.getMessage());
                ForgingAlarmReceiver.completeWakefulIntent(mIntent);
            }

            @Override
            public void onResponse(final Block block) {
                if (block != null && block.getTimestamp() > 0) {
                    CharSequence timeAgo = Utils.getTimeAgo(block.getTimestamp());
                    long diff = Utils.getTimeInMillisUntilNow(block.getTimestamp());

                    boolean notificationWithAlarm = diff > THIRTY_MINUTES_IN_MILLISECONDS;
                    sendNotification(timeAgo.toString(), notificationWithAlarm);

                    ForgingAlarmReceiver.completeWakefulIntent(mIntent);
                }
            }
        });
    }

    private void sendNotification(String msg, boolean warning) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.last_block_forged))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setLights(Color.RED, 1, 1)
                        .setAutoCancel(true)
                        .setContentText(msg);

        if (warning) {
            mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000});
            mBuilder.setColor(Color.RED);
        } else {
            int colorId = ContextCompat.getColor(this, R.color.colorPrimary);
            mBuilder.setColor(colorId);
        }

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

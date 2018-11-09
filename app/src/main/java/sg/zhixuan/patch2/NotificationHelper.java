package sg.zhixuan.patch2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String PATCH_APPT_CHANNEL_ID = "sg.zhixuan.noti.PATCHAPPT";
    private static final String PATCH_APPT_CHANNEL_NAME = "PATCHAPPT Channel";
    NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel patchApptChannel = new NotificationChannel(PATCH_APPT_CHANNEL_ID, PATCH_APPT_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        patchApptChannel.enableLights(true);
        patchApptChannel.enableVibration(true);
        patchApptChannel.setLightColor(Color.GREEN);
        patchApptChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(patchApptChannel);
    }

    public NotificationManager getManager() {

        if (manager == null) {
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), PATCH_APPT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        return builder;
    }
}

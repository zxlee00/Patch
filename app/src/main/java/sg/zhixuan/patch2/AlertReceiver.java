package sg.zhixuan.patch2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    String title, message;

    @Override
    public void onReceive(Context context, Intent intent) {

        title = "Appointment Scheduled";
        message = "You have an appointment scheduled within two hours. Please remember to check your appointment.";

        if (MainActivity.language.equals("Chinese")) {
            title = "预约";
            message = "您在两个钟头内有预约，请记得查看您的预约。";
        }

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getManager().notify(1, builder.build());
    }
}

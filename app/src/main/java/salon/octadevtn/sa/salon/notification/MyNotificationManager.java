package salon.octadevtn.sa.salon.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import salon.octadevtn.sa.salon.R;


public class MyNotificationManager {

    public static final int ID_SMALL_NOTIFICATION = 235;
    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public void showSmallNotification(String title, String message) {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.salon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.salon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.salon))
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message).setSummaryText(""))
                .build();

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);



    }




}


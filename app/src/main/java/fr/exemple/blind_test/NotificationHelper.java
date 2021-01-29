package fr.exemple.blind_test;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.content.Context;
import android.content.ContextWrapper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager notifManager;

    private static final String CHANNEL_HIGH_ID = "com.infinisoftware.testnotifs.HIGH_CHANNEL";
    private static final String CHANNEL_HIGH_NAME = "High Channel";

    private static final String CHANNEL_DEFAULT_ID = "com.infinisoftware.testnotifs.DEFAULT_CHANNEL";
    private static final String CHANNEL_DEFAUL_NAME = "Default Channel";
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private MediaSessionCompat mediaSessionCompat;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base ) {
        super( base );

        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long [] swPattern = new long[] { 0, 500, 110, 500, 110, 450, 110, 200, 110,
                170, 40, 450, 110, 200, 110, 170, 40, 500 };

        NotificationChannel notificationChannelHigh = new NotificationChannel(
                CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, notifManager.IMPORTANCE_HIGH );
        notificationChannelHigh.enableLights( true );
        notificationChannelHigh.setLightColor( Color.RED );
        notificationChannelHigh.setShowBadge( true );
        notificationChannelHigh.enableVibration( true );
        notificationChannelHigh.setVibrationPattern( swPattern );
        notificationChannelHigh.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );
        notifManager.createNotificationChannel( notificationChannelHigh );

        NotificationChannel notificationChannelDefault = new NotificationChannel(
                CHANNEL_DEFAULT_ID, CHANNEL_DEFAUL_NAME, notifManager.IMPORTANCE_DEFAULT );
        notificationChannelDefault.enableLights( true );
        notificationChannelDefault.setLightColor( Color.WHITE );
        notificationChannelDefault.enableVibration( true );
        notificationChannelDefault.setShowBadge( false );
        notifManager.createNotificationChannel( notificationChannelDefault );
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notify( int id, boolean prioritary, String title, String message ) {

        String channelId = prioritary ? CHANNEL_HIGH_ID : CHANNEL_DEFAULT_ID;
        Resources res = getApplicationContext().getResources();
        Context context = getApplicationContext();
        /* Lien avec l'activité à ouvrir : ici MainActivity */
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 456, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent1=new Intent(this,NotificationReceiver.class);
        //intent1.putExtra("toastMessage",message);
        PendingIntent pendingIntent1=PendingIntent.getService(this,0,
                intent1,PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap picture=BitmapFactory.decodeResource(getResources(),R.drawable.icone);
        Notification notification = new Notification.Builder( getApplicationContext(), channelId )
                .setContentIntent( contentIntent ) // clic sur la notif amène à l'activité
                .setContentTitle( title )
                .setContentText( message )
                .setSmallIcon( R.drawable.ic_audiotrack_black_24dp )
                .setLargeIcon(picture)
               // .addAction(R.mipmap.ic_launcher,"toast",actionIntent)
               /* .setStyle(new Notification.BigPictureStyle()
                .bigPicture(picture)              clic notif agrandissement image
                .bigLargeIcon((Bitmap) null))*/
                .addAction(R.drawable.ic_skip_previous_black_24dp,"Previous",null)
                .addAction(R.drawable.ic_play,"Play",null)
                .addAction(R.drawable.ic_pause,"Pause",pendingIntent1)
                .addAction(R.drawable.ic_skip_next_black_24dp,"Next",null)
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(1,2))
                .setAutoCancel( true )
                .build();
        notifManager.notify( id, notification );
    }

}

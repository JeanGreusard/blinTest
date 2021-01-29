package fr.exemple.blind_test;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      /*  String message=intent.getStringExtra("toastMessage");
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();*/
        MainActivity mainActivity=new MainActivity();
       mainActivity.mediaPlayer.pause();
    }
}

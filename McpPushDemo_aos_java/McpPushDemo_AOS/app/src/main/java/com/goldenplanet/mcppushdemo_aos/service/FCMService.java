package com.goldenplanet.mcppushdemo_aos.service;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.evergage.android.Evergage;
import com.goldenplanet.mcppushdemo_aos.activity.AlertActivity;
import com.goldenplanet.mcppushdemo_aos.util.LogMsg;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Kim Namhoon on 12/22/23.
 */

public class FCMService extends FirebaseMessagingService {
   @Override
   public void onNewToken(@NonNull String token) {
      super.onNewToken(token);
      LogMsg.i("onNewToken");
      Evergage.getInstance().setFirebaseToken(token);

   }

   @Override
   public void onMessageReceived(@NonNull RemoteMessage message) {
      super.onMessageReceived(message);
      sendNotification(message);

   }


   private void sendNotification(final RemoteMessage remoteMessage) {
      LogMsg.d("From: " + remoteMessage.getFrom());
      Map<String, String> data = remoteMessage.getData();
      if (data.size() > 0) {
         LogMsg.d( "Message data payload: " + remoteMessage.getData());
         RemoteMessage.Notification notification = remoteMessage.getNotification();
         if (notification == null) {
            return;
         }

         Intent alertIntent = new Intent(this, AlertActivity.class);
         alertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         alertIntent.putExtra("remoteMessage", remoteMessage);
         startActivity(alertIntent);

      }
   }
}

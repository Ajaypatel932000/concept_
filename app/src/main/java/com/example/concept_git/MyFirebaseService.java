package com.example.concept_git;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {
    public static  String TAG="Tag";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.



        if (remoteMessage.getNotification() != null) {
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationHelper.DisplayNotification(getApplicationContext(),title,body);
        }

    }

}


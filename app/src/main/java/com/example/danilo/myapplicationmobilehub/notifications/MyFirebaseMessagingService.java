package com.example.danilo.myapplicationmobilehub.notifications;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationDetails;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.activity.NavigationActivity;
import com.example.danilo.myapplicationmobilehub.fragment.RunFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("Notification data" + remoteMessage.getData());
        if (remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        else
            showNotification(remoteMessage.getData());
    }

    public static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationManager notificationManager;

    // Intent action used in local broadcast
    public static final String ACTION_PUSH_NOTIFICATION = "push-notification";
    // Intent keys
    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";

    private void showNotification(Map<String, String> data) {

        String title = data.get("title");
        String body = data.get("body");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "chanel.id";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        PendingIntent notifyPendingIntent = null;

        if (title.equals("ASSESSMENT_RUN_STARTED") || title.equals("ASSESSMENT_RUN_COMPLETED")) {
            final String runArn = data.get("runArn");

            Intent notifyIntent = new Intent(this, NavigationActivity.class);
            // Set the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notifyIntent.putExtra("runArn", runArn);
            // Create the PendingIntent
            notifyPendingIntent = PendingIntent.getActivity(
                    this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "chanel.id";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("Tokenfirebase", s);
    }

    //    @Override
//    public void onNewToken(String token) {
//        super.onNewToken(token);
//
//        Log.d(TAG, "Registering push notifications token: " + token);
//        NavigationActivity.getPinpointManager(getApplicationContext()).getNotificationClient().registerDeviceToken(token);
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        Log.d(TAG, "Message data: " + remoteMessage.getData());
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            Intent notificationIntent = new Intent(this, NavigationActivity.class);
//            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            final PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                    0 /* Request code */, notificationIntent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            //You should use an actual ID instead
//            int notificationId = new Random().nextInt(60000);
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                setupChannels();
//            }
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
//                            //.setLargeIcon(bitmap)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(remoteMessage.getNotification().getTitle())
//                            .setStyle(new NotificationCompat.BigPictureStyle()
//                                    .setSummaryText(remoteMessage.getData().get("message")))
//                            //.bigPicture(bitmap))/*Notification with Image*/
//                            .setContentText(remoteMessage.getNotification().getBody().toString())
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//            notificationManager.notify(notificationId, notificationBuilder.build());
//            return;
//
//        }
//
//
//    }
//
//    private void broadcast(final String from, final HashMap<String, String> dataMap) {
//        Intent intent = new Intent(ACTION_PUSH_NOTIFICATION);
//        intent.putExtra(INTENT_SNS_NOTIFICATION_FROM, from);
//        intent.putExtra(INTENT_SNS_NOTIFICATION_DATA, dataMap);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//    /**
//     * Helper method to extract push message from bundle.
//     *
//     * @param data bundle
//     * @return message string from push notification
//     */
//    public static String getMessage(Bundle data) {
//        return ((HashMap) data.get("data")).toString();
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setupChannels(){
//        NotificationChannel channel = new NotificationChannel("default",
//                "Channel name",
//                NotificationManager.IMPORTANCE_DEFAULT);
//        channel.setDescription("Channel description");
//        notificationManager.createNotificationChannel(channel);
//    }
}
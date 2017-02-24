package com.example.gek.servicedemo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.gek.servicedemo.R;

public class LiveService extends Service {
    public static final String TAG = "LIVE SERVICE";
    private int counter = 1;

    public LiveService() {
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        showMessage("onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved: ");
        showMessage("TaskRemoved");
        super.onTaskRemoved(rootIntent);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void showMessage(String mes){
        // Создаем с помощью билдера свое уведомление
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setContentIntent(null)
                .setSmallIcon(R.drawable.ic_car)
                .setAutoCancel(true)
                .setContentTitle("LiveService")
                .setContentText(mes)
                .setContentInfo(Integer.toString(counter));

        // Получаем непосрдественно класс Notification на основе нашего билдера
        Notification notification = builder.build();

        // Показываем наше уведомление через системный менеджер уведомлений
        NotificationManager nm =
                (NotificationManager)getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        nm.notify(counter++, notification);
    }

}

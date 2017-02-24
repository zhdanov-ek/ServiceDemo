package com.example.gek.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class SimpleService extends Service {
    public static final String TAG = "SIMPLE SERVICE";
    public SimpleService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        makeJob();
        makeJobAsync();
        super.onCreate();
    }

    // Срабатывает когда сервис запущен методом startService
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // задача запускаемная в основном потоке, в частности блокирующая ЮИ
    void makeJob() {
        for (int i = 1; i<=7; i++) {
            Log.d(TAG, "UI thread i = " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Задача в другом потоке, которая не блокирует ни ЮИ ни работу самого сервиса
    // После цикла останавливаем сервис. Эта задача если запуститься то будет выполняться до конца
    // даже если сервис остановим кнопкой с активити потому, что это отдельный поток.
    void makeJobAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i<=7; i++) {
                    Log.d(TAG, "Other thread i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Stop service via stopSelf()");
                stopSelf();
            }
        }).start();
    }
}

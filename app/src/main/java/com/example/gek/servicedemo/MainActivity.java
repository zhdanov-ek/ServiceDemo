package com.example.gek.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gek.servicedemo.services.LiveService;
import com.example.gek.servicedemo.services.SimpleService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int CODE_SUM_OPERATION = 55;
    public static final String EXTRA_RESULT = "result";
    public static final String TAG = "SERVICE DEMO (MA)";
    private Context ctx;

    public static final int NOTIFY_ID = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CODE_SUM_OPERATION) && (resultCode == RESULT_OK)) {
            ((TextView)findViewById(R.id.tvResult)).setText(data.getStringExtra(EXTRA_RESULT));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        findViewById(R.id.btnStartSimple).setOnClickListener(this);
        findViewById(R.id.btnStopSimple).setOnClickListener(this);
        findViewById(R.id.btnPendingIntent).setOnClickListener(this);
        findViewById(R.id.btnStartLiveService).setOnClickListener(this);
        findViewById(R.id.btnStopLiveService).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartSimple:
                startService(new Intent(this, SimpleService.class));
                break;

            case R.id.btnStopSimple:
                stopService(new Intent(this, SimpleService.class));
                break;

            case R.id.btnPendingIntent:
                Log.d(TAG, "onClick: startPendingIntent ");
                demoPendingIntent();
                break;

            case R.id.btnStartLiveService:
                Log.d(TAG, "onClick: startLiveService ");
                startService(new Intent(this, LiveService.class));
                break;

            case R.id.btnStopLiveService:
                Log.d(TAG, "onClick: stopLiveService ");
                stopService(new Intent(this, LiveService.class));
                break;

        }
    }


    // Демонстрация ожидающего интента через нотификейшн
    private void demoPendingIntent(){
        EditText etNumA = (EditText)findViewById(R.id.etNumA);
        EditText etNumB = (EditText)findViewById(R.id.etNumB);

        // Намерение, которое будет выполнено по клику на уведомлении
        Intent intentSecondActivity = new Intent(ctx, SecondActivity.class);

        // Ожидающее намерение, которое будет помещено в уведомление и будет ожидать клика
        PendingIntent pendingIntent = PendingIntent.getActivity(
                ctx,
                0,
                intentSecondActivity,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // Создаем с помощью билдера свое уведомление
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_car)
                .setAutoCancel(true)
                .setContentTitle("my tytle")
                .setContentText("My text : " + etNumA.getText())
                .setContentInfo("my info" + etNumB.getText());

        // Получаем непосрдественно класс Notification на основе нашего билдера
        Notification notification = builder.build();

        // Показываем наше уведомление через системный менеджер уведомлений
        NotificationManager nm =
                (NotificationManager)getSystemService(ctx.NOTIFICATION_SERVICE);
        nm.notify(NOTIFY_ID, notification);
    }
}

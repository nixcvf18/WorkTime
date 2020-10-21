package com.example.worktime.service;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.worktime.R;
import com.example.worktime.receiver.AlarmReceiver;
public class NotificationService extends Service {
    public NotificationService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");
        return  null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                popUpNotificationDialog();
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 7000, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }
    private void popUpNotificationDialog() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(getString(R.string.app_name));
        /*builder.setContentInfo(getString(R.string.noti_info));*/
            builder.setContentText(getString(R.string.app_name));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());//显示通知时间
        //Intent i = new Intent(this,MainActivity.class);
        //PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        //builder.setContentIntent(pi);
        //设置PendingIntent为打开MainActivity，问题是当点击了notification之后，
        // MainActivity会重新启动NotiService,此时仍然处在1分钟之内，通知在再次发送，造成通知的重复
        //builder.setOngoing(true);设置是否一直显示
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);//设置通知的声音、是否打开通知灯通知灯、是否振动为默认值
        notificationManager.notify(1, builder.build());
    }
}

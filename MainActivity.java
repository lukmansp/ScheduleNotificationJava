package com.example.schedulenotificationjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,RemindBroadcast.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

            AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
    long timeButtonClick = System.currentTimeMillis();
    long tenSecondInMilis =1000*10;
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeButtonClick+tenSecondInMilis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent);
        });
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Uri sound= Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+
                    "/raw/adzan_sound.mp3");
            CharSequence name = "amininReminderChannel";
            AudioAttributes audioAttributes=
                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_ALARM).build();
            String description ="Channel for Aminin";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyAminin",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
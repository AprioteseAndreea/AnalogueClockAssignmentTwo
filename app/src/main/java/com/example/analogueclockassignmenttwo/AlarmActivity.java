package com.example.analogueclockassignmenttwo;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    TextView info;
    Button cancelAlarm;
    final static int RQS_1 = 1;
    String alarmTime;

    public String getAlarmTime() {
        return alarmTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_alarm);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Alarms");
        }
        info = findViewById(R.id.info);
        pickerDate = findViewById(R.id.datepicker);
        pickerTime = findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = findViewById(R.id.setalarm);
        cancelAlarm = findViewById(R.id.cancel);
        buttonSetAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getHour(),
                        pickerTime.getMinute(),
                        0);

                if (cal.compareTo(current) <= 0) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time", Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }
            }

        });

        cancelAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                cancelAlarm();

            }

        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                "channel_1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    private void setAlarm(Calendar targetCal) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        alarmTime = sdf.format(targetCal.getTime());
        Toast.makeText(this, "Next alarm at " + alarmTime, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {

        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}

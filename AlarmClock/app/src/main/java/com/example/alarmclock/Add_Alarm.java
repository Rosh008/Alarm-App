package com.example.alarmclock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Add_Alarm extends AppCompatActivity {

    private TimePicker timePicker;
    public static final int alarmId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__alarm);

        timePicker = findViewById(R.id.time);


        Button add_alarm = findViewById(R.id.add_alarm);
        add_alarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String alarm_time = schedule_alarm();
                Intent intent = new Intent();
                intent.putExtra("time" , alarm_time);
                setResult(1 , intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String  schedule_alarm() {

        Alarm alarm = new Alarm(
                alarmId,
                timePicker.getHour(),
                timePicker.getMinute(),
                true
        );

        alarm.Schedule(getApplicationContext());

        return setDisplay();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private  String setDisplay()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

         @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a ");

        String submitDate = sd.format(calendar.getTime());

        return submitDate;
    }

}
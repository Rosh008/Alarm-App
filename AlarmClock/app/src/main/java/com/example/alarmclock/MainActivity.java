package com.example.alarmclock;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    public static   TextView display;

    public static final String time = "timeKey";

    private SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //file will be created
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //editor to edit file
        SharedPreferences.Editor editor;


        display = findViewById(R.id.alarm_info);
        // default value when user opens app say for first time
        display.setText(preferences.getString(time , "No Alarm Set"));



        ImageView imageView = findViewById(R.id.add_alarm);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to jump from one screen or activity to other
                Intent intent = new Intent(MainActivity.this , Add_Alarm.class);
                startActivityForResult(intent , 1);
            }
        });

        ImageView cancel = findViewById(R.id.cancel_alarm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm(MainActivity.this);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(time , "No Alarm Set");
                editor.apply();

                display.setText(preferences.getString(time , "No Alarm Set"));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String message = data.getStringExtra("time");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(time , message);
            editor.apply();

            display.setText(message);

        }
    }

    public void cancelAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, Add_Alarm.alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);

        String toastText = "Alarm cancelled";
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
    }
}
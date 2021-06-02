package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {

    private  ImageView imageView;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        imageView  = findViewById(R.id.clock_image);

        Button stop = findViewById(R.id.Stop);
        Button snooze = findViewById(R.id.snooze);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(MainActivity.time , "No Alarm Set");
                editor.apply();

                MainActivity.display.setText(sharedPreferences.getString(MainActivity.time , "No Alarm Set"));

                finish();
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 10);

                Alarm alarm = new Alarm(
                        new Random().nextInt(Integer.MAX_VALUE),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                );
                alarm.Schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);


                @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a ");
                String submitDate = sd.format(calendar.getTime());

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(MainActivity.time , submitDate);
                editor.apply();

                MainActivity.display.setText(sharedPreferences.getString(MainActivity.time , "No Alarm Set"));
                finish();

            }
        });

        animateClock();

    }



    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }


}
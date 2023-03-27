package com.example.simplestepdetectorandbmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private float x1,y1,x2,y2;
    private TextView stepdetector_text,distance_text,calorie_text;
    private SensorManager sensorManager;
    private Sensor mstepdetector;
    int stepdetect;
    SharedPreferences sharedPreferences;
    int calorie;
    float distance;
    private ImageButton imageButtonforbmicalc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        if(stepdetect==5000)
        {
            Toast.makeText(this, "You have completed 5000 steps", Toast.LENGTH_SHORT).show();
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        stepdetector_text=findViewById(R.id.stepdetector_text);
        distance_text=findViewById(R.id.distance_text);
        calorie_text=findViewById(R.id.calorie_text);
        imageButtonforbmicalc=findViewById(R.id.imageButtonforbmicalc);


        DecimalFormat decimalFormat=new DecimalFormat("#.##");

        String Cur_date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        sharedPreferences=this.getSharedPreferences("com.example.projectStepDetector", Context.MODE_PRIVATE);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
        {
            mstepdetector=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            sensorManager.registerListener(this,mstepdetector,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(this, "Detector Sensor Unavailable", Toast.LENGTH_SHORT).show();
        }

        if(!sharedPreferences.contains(Cur_date)) {



            sharedPreferences.edit().putInt(Cur_date, 0).apply();
            stepdetect = 0;
            calorie= (int) (stepdetect * 0.04);
            distance=stepdetect* 0.0008F;
            calorie_text.setText(String.valueOf(calorie));
            distance_text.setText(String.valueOf(decimalFormat.format(distance)));



        }
        else
        {
            stepdetect=sharedPreferences.getInt(Cur_date,1);
            stepdetector_text.setText(String.valueOf(stepdetect));
            calorie= (int) (stepdetect * 0.04);
            //distance 1 Step = 0.0008 Kilometre
            distance=stepdetect*.0008F;
            calorie_text.setText(String.valueOf(calorie));
            distance_text.setText(String.valueOf(decimalFormat.format(distance)));
        }


        imageButtonforbmicalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BMI_calc_activity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");


        if (mstepdetector == sensorEvent.sensor) {
            String Cur_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (sharedPreferences.contains(Cur_date)) {
                stepdetect = sharedPreferences.getInt(Cur_date, 1);
                stepdetect = (int) (stepdetect + sensorEvent.values[0]);
                stepdetector_text.setText(String.valueOf(stepdetect));
                sharedPreferences.edit().putInt(Cur_date, stepdetect).apply();
                calorie = (int) (stepdetect * 0.04);
                //distance 1 Step = 0.0008 Kilometre
                distance = stepdetect * .0008F;
                calorie_text.setText(String.valueOf(calorie));
                distance_text.setText(String.valueOf(decimalFormat.format(distance)));


            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
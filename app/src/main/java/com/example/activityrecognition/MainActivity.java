package com.example.activityrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SensorData sensorData;
    TextView accelerometerTxtView;
    TextView linearAccelTxtView;
    TextView magnetometerTxtView;
    TextView gyroscopeTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometerTxtView = findViewById(R.id.accelTxtView);
        linearAccelTxtView = findViewById(R.id.linearAccelerationTxtView);
        magnetometerTxtView = findViewById(R.id.magnetometerTxtView);
        gyroscopeTxtView = findViewById(R.id.gyroscopeTxtView);

        sensorData = new SensorData((SensorManager)getSystemService(SENSOR_SERVICE), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorData.registerSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorData.unregisterSensors();
    }

}
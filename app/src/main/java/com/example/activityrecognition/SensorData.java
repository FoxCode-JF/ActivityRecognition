package com.example.activityrecognition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;


public class SensorData implements SensorEventListener {
    private SensorManager sensorManager;
    private MainActivity mainActivity;
    private Sensor accelerometer;
    private Sensor linearAcceleration;
    private Sensor gyroscope;
    private Sensor magnetometer;

    private SensorValue accelerometerValues = new SensorValue(),
                        linearAccelerationValues = new SensorValue(),
                        gyroscopeValues = new SensorValue(),
                        magnetometerValues = new SensorValue();

    public SensorData (SensorManager sensorManager, MainActivity mainActivity) {
        this.sensorManager = sensorManager;
        this.mainActivity = mainActivity;
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    protected void registerSensors() {
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAcceleration, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, sensorManager.SENSOR_DELAY_NORMAL);
    }
    protected  void unregisterSensors() {
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            this.accelerometerValues.setValues(event.values.clone());
            mainActivity.accelerometerTxtView.setText("Accelerometer: X: " + this.accelerometerValues.getX());
        } else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            this.linearAccelerationValues.setValues(event.values.clone());
            mainActivity.linearAccelTxtView.setText("Linear acceleration: X: " + this.linearAccelerationValues.getX());
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            this.gyroscopeValues.setValues(event.values.clone());
            mainActivity.gyroscopeTxtView.setText("Gyroscope: X: " + this.gyroscopeValues.getX());
        } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            this.magnetometerValues.setValues(event.values.clone());
            mainActivity.magnetometerTxtView.setText("Magnetometer: X: " + this.magnetometerValues.getX());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    static class SensorValue {
        private float X;
        private float Y;
        private float Z;

        public SensorValue(){
            this.X = 0;
            this.Y = 0;
            this.Z = 0;
        }
        public SensorValue(float[] values)
        {
            this.X = values[0];
            this.Y = values[1];
            this.Z = values[2];
        }

        public void setValues(float[] values)
        {
            this.X = values[0];
            this.Y = values[1];
            this.Z = values[2];
        }

        public float getX() {
            return this.X;
        }

        public float getY() {
            return this.Y;
        }

        public float getZ() {
            return this.Z;
        }
    }

    public SensorValue getAccelerometerValues() {
        return this.accelerometerValues;
    }

    public SensorValue getGyroscopeValues() {
        return this.gyroscopeValues;
    }

    public SensorValue getMagnetometerValues() {
        return this.magnetometerValues;
    }

    public SensorValue getLinearAccelerationValues() {
        return this.linearAccelerationValues;
    }
}

package com.example.activityrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class MainActivity extends AppCompatActivity {

    SensorData sensorData;
    TextView accelerometerTxtView;
    TextView linearAccelTxtView;
    TextView magnetometerTxtView;
    TextView gyroscopeTxtView;
    TextView activityTxtView;
    ModelLoader modelLoader;
    int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 12;
    double[] dataInput = new double[12];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometerTxtView = findViewById(R.id.accelTxtView);
        linearAccelTxtView = findViewById(R.id.linearAccelerationTxtView);
        magnetometerTxtView = findViewById(R.id.magnetometerTxtView);
        gyroscopeTxtView = findViewById(R.id.gyroscopeTxtView);
        activityTxtView = findViewById(R.id.activityTxtView);

        sensorData = new SensorData((SensorManager) getSystemService(SENSOR_SERVICE), this);
        modelLoader = new ModelLoader();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorData.registerSensors();
    }

    public void predictions() {
        dataInput[0] = sensorData.getAccelerometerValues().getX();
        dataInput[1] = sensorData.getAccelerometerValues().getY();
        dataInput[2] = sensorData.getAccelerometerValues().getZ();
        dataInput[3] = sensorData.getGyroscopeValues().getX();
        dataInput[4] = sensorData.getGyroscopeValues().getY();
        dataInput[5] = sensorData.getGyroscopeValues().getZ();
        dataInput[6] = sensorData.getLinearAccelerationValues().getX();
        dataInput[7] = sensorData.getLinearAccelerationValues().getY();
        dataInput[8] = sensorData.getLinearAccelerationValues().getZ();
        dataInput[9] = sensorData.getMagnetometerValues().getX();
        dataInput[10] = sensorData.getMagnetometerValues().getY();
        dataInput[11] = sensorData.getMagnetometerValues().getZ();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://andreasvf.com/action_page.php";
        final String input = modelLoader.predict(dataInput);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("activity", input); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        activityTxtView.setText(input);
        MyRequestQueue.add(MyStringRequest);
    }





    @Override
    protected void onPause() {
        super.onPause();
        sensorData.unregisterSensors();
    }

    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }

}

package com.example.mylightsensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView imgView;
    private TextView txtView, txtValue;
    private Button btn;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        txtValue = findViewById(R.id.textValue);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void sensorOnOff(View view) {
        if (isStarted) {
            isStarted = false;
            sensorManager.unregisterListener(this);
            btn.setText("Light Sensor Off");
        } else {
            isStarted = true;
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            btn.setText("Light Sensor On");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
            txtValue.setText(Float.toString(lightValue));

            if (lightValue > 10) {
                imgView.setBackgroundResource(R.drawable.day);
                txtView.setText("Καλημέρα!");
                txtView.setBackgroundColor(Color.YELLOW);
                txtView.setTextColor(Color.DKGRAY);
            } else {
                imgView.setBackgroundResource(R.drawable.night);
                txtView.setText("Καληνύχτα!");
                txtView.setBackgroundColor(Color.DKGRAY);
                txtView.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }
}

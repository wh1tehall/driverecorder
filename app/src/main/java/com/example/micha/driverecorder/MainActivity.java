package com.example.micha.driverecorder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Handler customHandler = new Handler();
    private TextView timerValue;
    private Sensor acc;
    private double reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView)findViewById(R.id.textView1);
        Log.w("D:","BBB");
        acc = getSensor();
        Log.w("C:","AAA");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    private Sensor getSensor(){
        SensorManager mSensorManager;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor mSensor;

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return mSensor;

    }

    public void onSensorChanged(SensorEvent event){
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        x=x*x;
        y=y*y;
        z=z*z;
        reading = Math.sqrt(x+y+z);
        Log.w("B:",Double.toString(reading));
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            Log.w("A:",Double.toString(reading));

            timerValue.setText(Double.toString(reading));
            customHandler.postDelayed(this, 0);
        }

    };

}

package com.example.micha.driverecorder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener, Runnable {

    private Handler customHandler = new Handler();
    private TextView timerValue;
    private Sensor acc;
    private double reading;
    Thread worker;
    boolean work = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView)findViewById(R.id.textView1);
        Log.w("D:","BBB");
        acc = getSensor();

        Log.w("C:","AAA");
        worker = new Thread(this);
    }

    @Override
    protected void onResume() {
        work = true;
        worker.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        work = false;
        try{
            worker.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onPause();
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
        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_NORMAL);
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    Handler h = new Handler();
    @Override
    public void run() {
        while (work) {
            Log.w("A:", Double.toString(reading));
            h.post(new Runnable(){
                @Override public void run(){
                    timerValue.setText(String.format("%f",  reading));

                }
                });
           try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}

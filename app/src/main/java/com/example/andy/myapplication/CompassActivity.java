package com.example.andy.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView image;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float mCurrentDegree = 0f;
    private TextView lookingDegree;
    //private TextView happy;
    private TextView bu;
    //private MediaPlayer mp;
   // private MediaPlayer mp2;
    static final float ALPHA = 0.25f;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        // set device sensor capabilities
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
       mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
   //     happy =  (TextView) findViewById(R.id.happy);
        image = (ImageView) findViewById(R.id.pointer);
        bu = (TextView) findViewById(R.id.bu);
   //     mp = MediaPlayer.create(getApplicationContext(), R.raw.combo);
    //    mp2 = MediaPlayer.create(getApplicationContext(), R.raw.combo2);
    }


    protected void onResume(){
        super.onResume();
        //mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        //        SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);

    }

        // Save battery
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            mLastAccelerometer = lowPass(event.values.clone(), mLastAccelerometer);
            mLastAccelerometerSet = true;

        } else if (event.sensor == mMagnetometer) {
            mLastMagnetometer = lowPass(event.values.clone(), mLastMagnetometer);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(250);

            ra.setFillAfter(true);

            image.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;
            bu.setText(Float.toString(-mCurrentDegree));
        }

        // System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);

        //    System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);


         //   float degree = Math.round(event.values[0]);
        // lookingDegree.setText("Men");
      //  happy.setText("Cats");
        //bu.setText(Float.toString(degree1));



        // rotation animation


           // RotateAnimation ra = new RotateAnimation(
           //         mCurrentDegree,
           //         // reverse
           //         -degree,
           //         Animation.RELATIVE_TO_SELF, 0.5f,
            //        Animation.RELATIVE_TO_SELF,
             //       0.5f);
            // Animation duration
           // ra.setDuration(250);
            // set animation after
           // ra.setFillAfter(true);
            //Start animation
           // image.startAnimation(ra);
            // mCurrentDegree = -degree;


     //   if ( degree == 0){
    //        mp.start();
    //    }
    ///    else if ( degree == 180){
      ///      mp2.start();
    //    }
        }

    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    // not in use
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

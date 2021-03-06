package com.example.andy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Do when button is pressed */
    public void onButtonPress(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }
    public void onButtonPress2(View view) {
        Intent intent = new Intent(this, ValuesActivity.class);
        startActivity(intent);
    }

}

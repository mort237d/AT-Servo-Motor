package com.example.atservomotor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//Toturial: http://myandroidthings.com/post/tutorial-10
public class MainActivity extends Activity {
    private static final String TAG = "MAINACTIVITY";
    private static final String PWM_PIN = "PWM1";
    private Pwm servo;
    int i;
    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = 0;
        PeripheralManager peripheralManager = PeripheralManager.getInstance();

        try {
            //Åbner PWM1 og sætter frikvensen til 50Hz
            servo = peripheralManager.openPwm(PWM_PIN);
            servo.setPwmFrequencyHz(50);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Ny timer med tidsplan til at køre metoden NextMove
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                NextMove();
            }
        }, 0, 3000);
    }

    //Finder næste vinkel som servo skal indtage
    private void NextMove() {
        switch (i % 3) {
            case 0:
                SwingDegree(2);
                break;
            case 1:
                SwingDegree(7.5);
                break;
            case 2:
                SwingDegree(12.5);
                break;
        }
        i++;
    }

    //Servo får en vis hældning ud fra antal ms den kører
    private void SwingDegree(double inputInMs) {
        try {
            servo.setPwmDutyCycle(inputInMs);
            servo.setEnabled(true);
            Log.e(TAG,"Swing" + inputInMs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Lukker for servo
        if (servo != null) {
            try {
                servo.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    }
}

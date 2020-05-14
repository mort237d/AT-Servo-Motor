package com.example.atservomotor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.util.Timer;
import java.util.TimerTask;

//Toturial: http://myandroidthings.com/post/tutorial-10
public class MainActivity extends Activity {

    private static final String TAG = "MAT10";
    private static final String PWM_PIN = "PWM1";
    private Pwm mPwm;
    int i;
    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = 0;
        PeripheralManager pms = PeripheralManager.getInstance();
        try {
            mPwm = pms.openPwm(PWM_PIN);
            mPwm.setPwmFrequencyHz(50);
        } catch (Exception es) {

        }

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                NextMove();
            }
        }, 0, 3000);
    }

    private void NextMove() {
        switch (i % 3) {
            case 0:
                Swing0Degree();
                break;
            case 1:
                Swing90Degree();
                break;
            case 2:
                Swing180Degree();
                break;
        }
        i++;
    }

    private void Swing0Degree() {
        try {
            mPwm.setPwmDutyCycle(2);
            mPwm.setEnabled(true);
            Log.e(TAG,"Swing0");
        } catch (Exception ex) {

        }
    }

    private void Swing90Degree() {
        try {
            mPwm.setPwmDutyCycle(7.5);
            Log.e(TAG,"Swing90");
        } catch (Exception ex) {

        }
    }

    private void Swing180Degree() {
        try {
            mPwm.setPwmDutyCycle(12.5);
            Log.e(TAG,"Swing180");
        } catch (Exception ex) {

        }
    }
}
